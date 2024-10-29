package com.watchstore.apigateway.domainclientlayer.Stores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import com.watchstore.apigateway.presentationlayer.Stores.StoreRequestModel;
import com.watchstore.apigateway.presentationlayer.Stores.StoreResponseModel;
import com.watchstore.apigateway.utils.HttpErrorInfo;
import com.watchstore.apigateway.utils.exceptions.InvalidInputException;
import com.watchstore.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class StoresServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String STORES_SERVICE_BASE_URL;

    private StoresServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                @Value("${app.stores-service.host}") String storesServiceHost,
                                @Value("${app.stores-service.port}") String storesServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        STORES_SERVICE_BASE_URL = "HTTP://" + storesServiceHost + ":" + storesServicePort + "/api/v1/stores";


    }


    public List<StoreResponseModel> getAllStores(){
        try {
            String url = STORES_SERVICE_BASE_URL;

            StoreResponseModel[] storeResponseModels= restTemplate.getForObject(url,StoreResponseModel[].class);

            return Arrays.asList(storeResponseModels);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }
    public StoreResponseModel getStoreByStoreId(String storeId) {
        try {
            String url = STORES_SERVICE_BASE_URL + "/" + storeId;

            StoreResponseModel storeResponseModel = restTemplate.getForObject(url, StoreResponseModel.class);

            return storeResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public StoreResponseModel addStore (StoreRequestModel storeRequestModel) {
        try {
            String url = STORES_SERVICE_BASE_URL;

            StoreResponseModel storeResponseModel = restTemplate.postForObject(url, storeRequestModel, StoreResponseModel.class);

            return storeResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public StoreResponseModel updateStore(StoreRequestModel storeRequestModel, UUID storeId) {
        try {
            String url = STORES_SERVICE_BASE_URL + "/" + storeId;

            // Create an HttpEntity containing the request body
            HttpEntity<StoreRequestModel> requestEntity = new HttpEntity<>(storeRequestModel);

            // Perform a PUT request using RestTemplate.exchange()
            ResponseEntity<StoreResponseModel> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    StoreResponseModel.class
            );

            // Extract and return the EmployeeResponseModel from the response entity
            return responseEntity.getBody();

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteStore(UUID storeId) {
        try {
            String url = STORES_SERVICE_BASE_URL + "/" + storeId;

            // Perform DELETE request
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }
    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
//include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
