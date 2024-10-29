package com.watchstore.inventory.presentationlayer;

import com.watchstore.inventory.dataaccesslayer.watch.Feature;
import com.watchstore.inventory.dataaccesslayer.watch.Status;
import com.watchstore.inventory.dataaccesslayer.watch.Watch;
import com.watchstore.inventory.dataaccesslayer.watch.WatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("h2")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WatchControllerIntegrationTest {

    private final String BASE_URI_WATCHES = "api/v1/inventories";
    private final String FOUND_WATCH_REFERENCE_NUMBER = "a1913a79-5151-5151-9ffd-06578e7a1234";

    private final String FOUND_WATCH_BRAND = "rolex";
    private final String FOUND_WATCH_COLOR = "GOLD";

    private final String NOT_FOUND_WATCH_REFERENCE_NUMBER = "05c8ab76-4f75-45c1-b6e2-aa8e91488888";
    private final String INVALID_WATCH_REFERENCE_NUMBER = "05c8ab76-4f75-45c1-b6e2";

    @Autowired
    private WatchRepository watchRepository;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void whenGetWatches_thenReturnAllWatchesByInventory() {
        //arrange
        // should be expectedNumberFound
        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a4321");
        int expectedNumberFound = 5;

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a4321" + "/watches";
        //act and assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(WatchResponseModel.class)
                .value((list) ->{
                    //assert


                    //Compare the expected with the actual
                    assertNotNull(list);
                    assertEquals(expectedNumberFound, list.size());
                });
    }

    @Test
    public void whenGetWatches_thenReturnAllWatchesByInventory1() {
        //arrange
        // should be expectedNumberFound
        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a7894");
        int expectedNumberFound = 3;

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a7894" + "/watches";
        //act and assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(WatchResponseModel.class)
                .value((list) -> {
                    //assert


                    //Compare the expected with the actual
                    assertNotNull(list);
                    assertEquals(expectedNumberFound, list.size());
                });

    }

    @Test
    public void whenGetWatches_thenReturnAllWatchesByInventory2() {
        //arrange
        // should be expectedNumberFound
        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a1234");
        int expectedNumberFound = 2;

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a1234" + "/watches";
        //act and assert
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(WatchResponseModel.class)
                .value((list) -> {
                    //assert
                    //Compare the expected with the actual
                    assertNotNull(list);
                    assertEquals(expectedNumberFound, list.size());
                });

    }

    @Test
    public void whenGetWatchReferenceNumber_ThenReturnWatch(){
        //arrange
        Watch watch = watchRepository.findWatchByWatchIdentifier_ReferenceNumber(FOUND_WATCH_REFERENCE_NUMBER);
        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a4321" + "/watches" + "/" + FOUND_WATCH_REFERENCE_NUMBER;
        //act
        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(WatchResponseModel.class)
                .value((response) ->{
                    //assert
                    assertNotNull(response); //always check if the value expected is not null
                    assertEquals(response.getReferenceNumber().toString(), watch.getWatchIdentifier().getReferenceNumber());
                    assertEquals(response.getBrand(), watch.getBrand());
                    assertEquals(response.getColor(), watch.getColor());
                });

    }

//    @Test
//    public void whenGetWatchDoesNotExist_thenReturnNotFound() {
//        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a7894");
//
//
//        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a7894" + "/watches" + "/" + NOT_FOUND_WATCH_REFERENCE_NUMBER;
//        //act and assert
//        webTestClient.get()
//                .uri(url)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isNotFound()
//                .expectBody()
//                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
//                .jsonPath("$.message").isEqualTo("No watch found with ID: " + NOT_FOUND_WATCH_REFERENCE_NUMBER);
//    }


    @Test
    public void whenValidWatch_ThenCreateWatch() {
        // Arrange

        UUID inventoryId = UUID.fromString("e5913a79-5151-5151-9ffd-06578e7a7894");
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        WatchRequestModel watchRequestModel = new WatchRequestModel("a1913a79-5151-5151-9ffd-06578e700000", "rolex", "Submarine", "Silver", 2008, Status.AVAILABLE, features);

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a1234" + "/watches";
        // Act and Assert
        webTestClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(watchRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(WatchResponseModel.class)
                .value((watchResponseModel) -> {
                    assertNotNull(watchResponseModel);
                    assertEquals(watchResponseModel.getBrand(), watchResponseModel.getBrand());
                    assertEquals(watchResponseModel.getModel(), watchResponseModel.getModel());
                    assertEquals(watchResponseModel.getColor(), watchResponseModel.getColor());
                    assertEquals(watchResponseModel.getYear(), watchResponseModel.getYear());
                    assertNotNull(watchResponseModel.getFeatures());
                    assertEquals(1, watchResponseModel.getFeatures().size()); // Assuming one feature is added
                    // Add more assertions as needed for other fields
                });
    }

    @Test
    public void whenUpdateWatch_thenReturnOK(){
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        WatchRequestModelUpdate watchRequestModelUpdate = new WatchRequestModelUpdate("rolex", "Submarine", "Silver", 2008, Status.AVAILABLE, features);

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a4321" + "/watches" + "/" + FOUND_WATCH_REFERENCE_NUMBER;

        //act and assert
        webTestClient.put()
                .uri(url )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(watchRequestModelUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(WatchResponseModel.class)
                .value((watchResponseModel) -> {
                    assertNotNull(watchResponseModel);
                    assertEquals(watchResponseModel.getBrand(), watchResponseModel.getBrand());
                    assertEquals(watchResponseModel.getModel(), watchResponseModel.getModel());
                    assertEquals(watchResponseModel.getColor(), watchResponseModel.getColor());
                    assertEquals(watchResponseModel.getYear(), watchResponseModel.getYear());
                    assertNotNull(watchResponseModel.getFeatures());
                    assertEquals(1, watchResponseModel.getFeatures().size());
    });

}
    @Test
    public void whenUpdateWatchDoesNotExist_thenReturnNotFound(){
        List<Feature> features = Arrays.asList(new Feature("Gold", 44, "Solar", 60000.0));
        WatchRequestModelUpdate watchRequestModelUpdate = new WatchRequestModelUpdate("rolex", "Submarine", "Silver", 2008, Status.AVAILABLE, features);

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a7894" + "/watches" + "/" + FOUND_WATCH_REFERENCE_NUMBER;

        //act and assert
        webTestClient.put()
                .uri(url )
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(watchRequestModelUpdate)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    public void whenDeleteWatchDoesNotExist_thenReturnException(){
        //act and assert

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a7894" + "/watches" + "/" + NOT_FOUND_WATCH_REFERENCE_NUMBER;
        webTestClient.delete()
                .uri(url)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No watch found with ID: " + NOT_FOUND_WATCH_REFERENCE_NUMBER);

    }

    @Test
    public void whenDeleteWatch_thenReturnNoContent(){
        //act and assert

        String url = BASE_URI_WATCHES + "/" + "e5913a79-5151-5151-9ffd-06578e7a4321" + "/watches" + "/" + FOUND_WATCH_REFERENCE_NUMBER;
        webTestClient.delete()
                .uri(url)
                .exchange()
                .expectStatus().isNoContent();

    }
}



