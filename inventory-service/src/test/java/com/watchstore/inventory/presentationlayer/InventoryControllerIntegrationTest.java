package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.dataaccesslayer.inventory.Inventory;
import com.watchstore.inventory.dataaccesslayer.inventory.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InventoryControllerIntegrationTest {

    private final String BASE_URI_INVENTORIES = "/api/v1/inventories";
    private final String FOUND_INVENTORY_ID = "e5913a79-5151-5151-9ffd-06578e7a4321";

    private final String FOUND_INVENTORY_TYPE = "Men";

    private final String NOT_FOUND_INVENTORY_ID = "e5913a79-5151-5151-9ffd-06578e700000";
    private final String INVALID_INVENTORY_ID = "e5913a79-5151-5151";

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetInventories_thenReturnAllInventories() {
        //arrange
        Long sizeDB = inventoryRepository.count();

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_INVENTORIES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(InventoryResponseModel.class)
                .value((list) -> {
                    //assert
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });

    }

    @Test
    public void whenGetInventoryById_thenReturnInventory() {
        //
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(FOUND_INVENTORY_ID);


        //act and assert
        webTestClient.get()
                .uri(BASE_URI_INVENTORIES + "/" + FOUND_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)//web client is sending this type of media
                .exchange()// actually send the request
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)//return type is json
                .expectBody(InventoryResponseModel.class)//expect the return of a list
                .value((response) -> {
                    //assert
                    assertNotNull(response); //always check if the value expected is not null
                    assertEquals(response.getInventoryId().toString(), inventory.getInventoryIdentifier().getInventoryId());

                });

    }

    @Test
    public void whenGetInventoryDoesNotExists_thenReturnNotFound() {
        //act and assert
        webTestClient.get()
                .uri(BASE_URI_INVENTORIES + "/" + NOT_FOUND_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknow inventory" + NOT_FOUND_INVENTORY_ID); //same message as in serviceimpl

    }

    @Test
    public void whenValidInventory_thenReturnInventory() {
        //arrange
        long sizeDB = inventoryRepository.count();

        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel("Men");

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_INVENTORIES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(InventoryResponseModel.class)
                .value((inventoryResponseModel) -> {
                    assertNotNull(inventoryResponseModel);
                    assertEquals(inventoryResponseModel.getType(), inventoryRequestModel.getType());

                });

        long sizeDBAfter = inventoryRepository.count();
        assertEquals(sizeDB + 1, sizeDBAfter);
    }

    @Test
    public void whenInventoryExists_thenUpdateInventory() {
        long sizeDB = inventoryRepository.count();
        Inventory inventory = inventoryRepository.findInventoryByInventoryIdentifier_InventoryId(FOUND_INVENTORY_ID);

        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel("Men");

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_INVENTORIES + "/" + FOUND_INVENTORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(InventoryResponseModel.class)
                .value((inventoryResponseModel) -> {
                    assertNotNull(inventoryResponseModel);
                    assertEquals(inventoryResponseModel.getType(), inventoryRequestModel.getType());


                });
        long sizeDBAfter = inventoryRepository.count();
        assertEquals(sizeDB, sizeDBAfter);
    }

    @Test
    public void whenUpdateInventoryDoesNotExists_thenException() {
        //ARRANGE
        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel("Men");
        //act and assert
        webTestClient.put()
                .uri(BASE_URI_INVENTORIES + "/" + NOT_FOUND_INVENTORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Cannot update id: " + NOT_FOUND_INVENTORY_ID);


    }

    @Test
    public void whenInventoryExists_thenDeleteInventory() {
        //arrange
        long sizeDB = inventoryRepository.count();

        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_INVENTORIES + "/" + FOUND_INVENTORY_ID)
                .exchange()
                .expectStatus().isNoContent();

        long sizeDBAfter = inventoryRepository.count();
        assertEquals(sizeDB - 1, sizeDBAfter);


    }

    @Test
    public void whenDeleteInventoryDoesNotExist_thenException() {
        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_INVENTORIES + "/" + NOT_FOUND_INVENTORY_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No inventory found with ID: " + NOT_FOUND_INVENTORY_ID);

    }

}