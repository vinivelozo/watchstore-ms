package com.watchstore.apigateway.domainclientlayer.Inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import com.watchstore.apigateway.presentationlayer.Inventory.*;
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
public class WatchServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String WATCH_SERVICE_BASE_URL;

    private WatchServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                               @Value("${app.inventory-service.host}") String inventoryServiceHost,
                               @Value("${app.inventory-service.port}") String inventoryServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        WATCH_SERVICE_BASE_URL = "HTTP://" + inventoryServiceHost + ":" + inventoryServicePort + "/api/v1/inventories";
    }

    public List<WatchResponseModel> getAllWatches(UUID inventoryId){

        // implement inventoryId here
        try {
            String url = WATCH_SERVICE_BASE_URL + "/" + inventoryId + "/watches";

            WatchResponseModel[] watchResponseModels= restTemplate.getForObject(url,WatchResponseModel[].class);

            return Arrays.asList(watchResponseModels);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }

    }
    public WatchResponseModel getWatchByInventoryIdAndByReferenceNumber(UUID inventoryId, String referenceNumber) {
        try {
            String url = WATCH_SERVICE_BASE_URL + "/" + inventoryId + "/watches/" + referenceNumber;

            WatchResponseModel watchResponseModel = restTemplate.getForObject(url, WatchResponseModel.class);

            return watchResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public WatchResponseModel addWatch (UUID inventoryId, WatchRequestModel watchRequestModel) {
        try {
            String url = WATCH_SERVICE_BASE_URL + "/" + inventoryId + "/watches";

            WatchResponseModel watchResponseModel = restTemplate.postForObject(url, watchRequestModel, WatchResponseModel.class);

            return watchResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public WatchResponseModel updateWatch(WatchRequestModelUpdate watchRequestModelUpdate, UUID inventoryId, String referenceNumber) {
        try {
            String url = WATCH_SERVICE_BASE_URL + "/" + inventoryId + "/watches" + "/" + referenceNumber;

            // Create an HttpEntity containing the request body
            HttpEntity<WatchRequestModelUpdate> requestEntity = new HttpEntity<>(watchRequestModelUpdate);

            // Perform a PUT request using RestTemplate.exchange()
            ResponseEntity<WatchResponseModel> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    WatchResponseModel.class
            );


            return responseEntity.getBody();

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteWatch( UUID inventoryId, String referenceNumber) {
        try {
            String url = WATCH_SERVICE_BASE_URL + "/" + inventoryId + "/watches" + "/" + referenceNumber;

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



