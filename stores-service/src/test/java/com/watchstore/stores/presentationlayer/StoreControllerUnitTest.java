package com.watchstore.stores.presentationlayer;

import com.watchstore.stores.businesslayer.StoreService;
import com.watchstore.stores.dataaccesslayer.Address;
import com.watchstore.stores.dataaccesslayer.PhoneNumber;
import com.watchstore.stores.dataaccesslayer.PhoneType;
import com.watchstore.stores.utils.GlobalControllerExceptionHandler;
import com.watchstore.stores.utils.HttpErrorInfo;
import com.watchstore.stores.utils.exceptions.InvalidInputException;
import com.watchstore.stores.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StoreController.class)
class StoreControllerUnitTest {

        private final String FOUND_STORE_ID ="e5913a79-9b1e-4516-9ffd-06578e7af261";
        private final String NOT_FOUND_STORE_ID ="05c8ab76-4f75-45c4-b6e2-aa8e91488000";

        private final String INVALID_STORE_ID ="05c8ab76-4f75-45c1-b6e2";

        @Autowired
        private StoreController storeController;

        @MockBean
        private StoreService storeService;

    @Test
    public void whenStoreExists_thenReturnEmptyList(){
        //arrange
        when(storeService.getAllStores()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<StoreResponseModel>> responseEntity = storeController.getAllStores();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(storeService, times(1)).getAllStores();
    }

    @Test
    public void whenStoreExists_thenReturnStoreById(){

        //arrange
        when(storeService.getStoreById(FOUND_STORE_ID)).thenReturn(new StoreResponseModel());

        //act
        ResponseEntity<StoreResponseModel> responseEntity = storeController.getStoreById("e5913a79-9b1e-4516-9ffd-06578e7af261");

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(storeService, times(1)).getStoreById("e5913a79-9b1e-4516-9ffd-06578e7af261");
    }

    @Test
    public void testGetAllStores() {
        // Arrange
        when(storeService.getAllStores()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<StoreResponseModel>> responseEntity = storeController.getAllStores();

        // Assert
        // Check if response status is OK
        assertEquals(200, responseEntity.getStatusCodeValue());


    }
//    @PostMapping()
//    public ResponseEntity<StoreResponseModel> addStore(@RequestBody StoreRequestModel storeRequestModel){
//        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addStore(storeRequestModel));
    @Test
    public void whenAddingStore_thenReturnStore(){


        //arrange

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-555-8888"));
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE2, "514-575-4545"));

        StoreRequestModel storeRequestModel = StoreRequestModel.builder()
                .address(new Address("123 rue laplame", "Longueuil", "Quebec", "Canada", "J5G7R"))
                .email("hsds@gmail.com")
                .phoneNumbers(phoneNumbers)
                .build();

        StoreResponseModel storeResponseModel = StoreResponseModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .address(new Address("123 rue laplame", "Longueuil", "Quebec", "Canada", "J5G7R"))
                .email("hsds@gmail.com")
                .phoneNumbers(phoneNumbers)
                .build();

        when(storeService.addStore(storeRequestModel)).thenReturn(storeResponseModel);

        //act
        ResponseEntity<StoreResponseModel> responseEntity = storeController.addStore(storeRequestModel);

        //assert

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody().getAddress().getCity(), storeRequestModel.getAddress().getCity());
        assertEquals(responseEntity.getBody().getAddress().getCountry(), storeRequestModel.getAddress().getCountry());
        assertEquals(responseEntity.getBody().getAddress().getStreet(), storeRequestModel.getAddress().getStreet());
        assertEquals(responseEntity.getBody().getAddress().getProvince(), storeRequestModel.getAddress().getProvince());
        assertEquals(responseEntity.getBody().getAddress().getPostal_code(), storeRequestModel.getAddress().getPostal_code());
        assertEquals(responseEntity.getBody().getEmail(), storeRequestModel.getEmail());
        assertEquals(responseEntity.getBody().getPhoneNumbers(), storeRequestModel.getPhoneNumbers());
        assertEquals(responseEntity.getBody().getStoreId().length(), 36);
        assertEquals(responseEntity.getBody().getStoreId(),"e5913a79-9b1e-4516-9ffd-06578e7af261" );


    }

    @Test
    public void whenStoreExists_thenDeleteStore() {
        //arrange
        doNothing().when(storeService).deleteStore(UUID.fromString(FOUND_STORE_ID));

        //act
        ResponseEntity<Void> responseEntity = storeController.deleteStore(UUID.fromString(FOUND_STORE_ID));

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(storeService, times(1)).deleteStore(UUID.fromString(FOUND_STORE_ID));
    }

    @Test
    public void whenStoreExists_thenReturnUpdatedStore(){
        //arrange
        StoreRequestModel storeRequestModel = buildStoreRequestModel();
        StoreResponseModel storeResponseModel = buildStoreResponseModel();

        when(storeService.updateStore(storeRequestModel, UUID.fromString(FOUND_STORE_ID))).thenReturn(storeResponseModel);

        //act
        ResponseEntity<StoreResponseModel> responseEntity = storeController.updateStore(UUID.fromString(FOUND_STORE_ID), storeRequestModel);


        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(storeResponseModel, responseEntity.getBody());
        verify(storeService, times(1)).updateStore(storeRequestModel, UUID.fromString(FOUND_STORE_ID));

    }

    private StoreRequestModel buildStoreRequestModel() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();

        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-555-5555"));
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE2, "514-555-5555"));

        return StoreRequestModel.builder()
                .address(new Address("123 rue laplame", "Longueuil", "Quebec", "Canada", "J5G7R"))
                .email("hsds@gmail.com")
                .phoneNumbers(phoneNumbers)
                .build();
    }

    private StoreResponseModel buildStoreResponseModel() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();

        phoneNumbers.add(new PhoneNumber(PhoneType.STORE1, "514-555-5555"));
        phoneNumbers.add(new PhoneNumber(PhoneType.STORE2, "514-555-5555"));

        return StoreResponseModel.builder()
                .storeId("e5913a79-9b1e-4516-9ffd-06578e7af261")
                .address(new Address("123 rue laplame", "Longueuil", "Quebec", "Canada", "J5G7R"))
                .email("hsds@gmail.com")
                .phoneNumbers(phoneNumbers)
                .build();
    }

    @Test
    public void testInvalidInputExceptionWithoutMessage() {
        InvalidInputException exception = new InvalidInputException();

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testInvalidInputExceptionWithMessage() {
        String message = "Invalid input";

        InvalidInputException exception = new InvalidInputException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testInvalidInputExceptionWithCause() {
        String message = "Invalid input";
        Throwable cause = new IllegalArgumentException("Cause");

        InvalidInputException exception = new InvalidInputException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testNotFoundExceptionWithoutMessage() {
        NotFoundException exception = new NotFoundException();

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testNotFoundExceptionWithMessage() {
        String message = "Not found";

        NotFoundException exception = new NotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testNotFoundExceptionWithCause() {
        String message = "Not found";
        Throwable cause = new IllegalArgumentException("Cause");

        NotFoundException exception = new NotFoundException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void handleNotFoundException_ReturnsCorrectHttpStatusAndMessage() {
        // Arrange
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Exception exception = new NotFoundException("Employee not found");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

        // Act
        HttpErrorInfo errorInfo = handler.handleNotFoundException(webRequest, exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, errorInfo.getHttpStatus());
        assertEquals("Employee not found", errorInfo.getMessage());
    }

    @Test
    public void handleInvalidInputException_ReturnsCorrectHttpStatusAndMessage() {
        // Arrange
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Exception exception = new InvalidInputException("Invalid input");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

        // Act
        HttpErrorInfo errorInfo = handler.handleInvalidInputException(webRequest, exception);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorInfo.getHttpStatus());
        assertEquals("Invalid input", errorInfo.getMessage());
    }


}