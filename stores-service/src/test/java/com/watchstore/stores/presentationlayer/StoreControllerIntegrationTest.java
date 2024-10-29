package com.watchstore.stores.presentationlayer;

import com.watchstore.stores.dataaccesslayer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StoreControllerIntegrationTest {
    private final String BASE_URI_STORES = "/api/v1/stores";
    private final String FOUND_STORE_ID = "e5913a79-9b1e-4516-9ffd-06578e7af261";

    private final String FOUND_STORE_TYPE = "Men";

    private final String NOT_FOUND_STORE_ID = "e5913a79-9b1e-4516-9ffd-065780000000";
    private final String INVALID_STORE_ID = "e5913a79-9b1e-4516-9ffd";

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetStores_thenReturnAllStores() {
        //arrange
        Long sizeDB = storeRepository.count();

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_STORES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(StoreResponseModel.class)
                .value((list) -> {
                    //assert
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });

    }

    @Test
    void whenGetStoreById_thenReturnStore() {
        //arrange
        Store store = storeRepository.findStoreByStoreIdentifier_StoreId(FOUND_STORE_ID);

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_STORES + "/" + FOUND_STORE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(StoreResponseModel.class)
                .value((response) -> {
                    //assert
                    assertNotNull(response); //always check if the value expected is not null
                    assertEquals(response.getStoreId().toString(), store.getStoreIdentifier().getStoreId());
                    assertEquals(response.getEmail(), store.getEmail());
                    assertEquals(response.getAddress().getPostal_code(), store.getAddress().getPostal_code());
                    assertEquals(response.getAddress().getCity(), store.getAddress().getCity());
                    assertEquals(response.getAddress().getCountry(), store.getAddress().getCountry());
                    assertEquals(response.getAddress().getStreet(), store.getAddress().getStreet());


                });

    }

    @Test
    public void whenGetStoreDoesNotExist_thenReturnNotFound() {
        //act and assert
        webTestClient.get()
                .uri(BASE_URI_STORES + "/" + NOT_FOUND_STORE_ID)//what is the uri?
                .accept(MediaType.APPLICATION_JSON)//web client is sending this type of media
                .exchange()// actually send the request
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Store id invalid" + NOT_FOUND_STORE_ID); //same message as in serviceimpl
    }

    @Test
    public void whenValidStore_thenCreateStore() {
        //arrange
        long sizeDB = storeRepository.count();
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));

        StoreRequestModel storeRequestModel = new StoreRequestModel(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "vini02@outlook.com", phoneNumbers);

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_STORES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(StoreResponseModel.class)
                .value((storeResponseModel) -> {
                    assertNotNull(storeResponseModel);
                    assertEquals(storeResponseModel.getAddress().getStreet(), storeRequestModel.getAddress().getStreet());
                    assertEquals(storeResponseModel.getAddress().getCity(), storeRequestModel.getAddress().getCity());
                    assertEquals(storeResponseModel.getAddress().getPostal_code(), storeRequestModel.getAddress().getPostal_code());
                    assertEquals(storeResponseModel.getAddress().getProvince(), storeRequestModel.getAddress().getProvince());
                    assertEquals(storeResponseModel.getAddress().getCountry(), storeRequestModel.getAddress().getCountry());
                    assertEquals(storeResponseModel.getEmail(), storeRequestModel.getEmail());
                    assertEquals(storeResponseModel.getPhoneNumbers().size(), storeRequestModel.getPhoneNumbers().size());
                });
        long sizeDBAfter = storeRepository.count();
        assertEquals(sizeDB + 1, sizeDBAfter);

    }

    @Test
    public void whenStoreExists_thenUpdateStore(){
        long sizeDB = storeRepository.count();
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));

        StoreRequestModel storeRequestModel = new StoreRequestModel(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "vini02@outlook.com", phoneNumbers);
        webTestClient.put()
                .uri(BASE_URI_STORES + "/" + FOUND_STORE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(StoreResponseModel.class)
                .value((storeResponseModel) -> {
                    assertNotNull(storeResponseModel);
                    assertNotNull(storeResponseModel);
                    assertEquals(storeResponseModel.getAddress().getStreet(), storeRequestModel.getAddress().getStreet());
                    assertEquals(storeResponseModel.getAddress().getCity(), storeRequestModel.getAddress().getCity());
                    assertEquals(storeResponseModel.getAddress().getPostal_code(), storeRequestModel.getAddress().getPostal_code());
                    assertEquals(storeResponseModel.getAddress().getProvince(), storeRequestModel.getAddress().getProvince());
                    assertEquals(storeResponseModel.getAddress().getCountry(), storeRequestModel.getAddress().getCountry());
                    assertEquals(storeResponseModel.getEmail(), storeRequestModel.getEmail());
                    assertEquals(storeResponseModel.getPhoneNumbers().size(), storeRequestModel.getPhoneNumbers().size());
                });

        long sizeDBAfter = storeRepository.count();
        assertEquals(sizeDB, sizeDBAfter);

    }

    @Test
    public void whenUpdateStoreDoesNotExist_thenReturnException(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-456-7990"));

        StoreRequestModel storeRequestModel = new StoreRequestModel(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "vini02@outlook.com", phoneNumbers);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_STORES + "/" + NOT_FOUND_STORE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(storeRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Cannot update id: " + NOT_FOUND_STORE_ID);

    }

    @Test
    public void whenStoreExists_thenDeleteStore(){
        //arrange
        long sizeDB = storeRepository.count();

        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_STORES + "/" + FOUND_STORE_ID)
                .exchange()
                .expectStatus().isNoContent();

        long sizeDBAfter = storeRepository.count();
        assertEquals(sizeDB - 1, sizeDBAfter);
    }

    @Test
    public void whenDeleteStoreDoesNotExist_thenThrowException(){
        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_STORES + "/" + NOT_FOUND_STORE_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No store found with ID: " + NOT_FOUND_STORE_ID);
    }
}

