package com.watchstore.apigateway.domainclientlayer.Inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;
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
public class InventoryServiceClient {


    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String INVENTORY_SERVICE_BASE_URL;

    private InventoryServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                   @Value("${app.inventory-service.host}") String inventoryServiceHost,
                                   @Value("${app.inventory-service.port}") String inventoryServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        INVENTORY_SERVICE_BASE_URL = "HTTP://" + inventoryServiceHost + ":" + inventoryServicePort + "/api/v1/inventories";


    }

    public List<InventoryResponseModel> getAllInventories(){
        try {
            String url = INVENTORY_SERVICE_BASE_URL;

            InventoryResponseModel[] inventoryResponseModels= restTemplate.getForObject(url,InventoryResponseModel[].class);

            return Arrays.asList(inventoryResponseModels);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }
    public InventoryResponseModel getInventoryByInventoryId(String inventoryId) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/"  + inventoryId;

            InventoryResponseModel inventoryResponseModel = restTemplate.getForObject(url, InventoryResponseModel.class);

            return inventoryResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public InventoryResponseModel addInventory (InventoryRequestModel inventoryRequestModel) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL;

            InventoryResponseModel inventoryResponseModel = restTemplate.postForObject(url, inventoryRequestModel, InventoryResponseModel.class);

            return inventoryResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, UUID inventoryId) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;

            // Create an HttpEntity containing the request body
            HttpEntity<InventoryRequestModel> requestEntity = new HttpEntity<>(inventoryRequestModel);

            // Perform a PUT request using RestTemplate.exchange()
            ResponseEntity<InventoryResponseModel> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    InventoryResponseModel.class
            );

            // Extract and return the EmployeeResponseModel from the response entity
            return responseEntity.getBody();

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteInventory(UUID inventoryId) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;

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
