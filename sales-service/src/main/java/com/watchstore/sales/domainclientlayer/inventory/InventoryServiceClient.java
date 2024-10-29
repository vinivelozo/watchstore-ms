package com.watchstore.sales.domainclientlayer.inventory;

import com.watchstore.sales.domainclientlayer.watch.WatchModel;
import com.watchstore.sales.domainclientlayer.watch.WatchResponseModel;
import com.watchstore.sales.utils.HttpErrorInfo;
import com.watchstore.sales.utils.exceptions.InvalidInputException;
import com.watchstore.sales.utils.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    public WatchModel getWatchByReferenceNumberAndInventoryId(String inventoryId, String referenceNumber) { // it was getInventoryBuInventoryId
        try {
            //String url = INVENTORY_SERVICE_BASE_URL + "/" +  inventoryId ;
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId + "/" + "watches" + "/" + referenceNumber;

            WatchModel watchModel = restTemplate.getForObject(url, WatchModel.class);

            return watchModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public InventoryModel getInventoryByInventoryId(String inventoryId) { // it was getInventoryBuInventoryId
        try {
            //String url = INVENTORY_SERVICE_BASE_URL + "/" +  inventoryId ;
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;

            InventoryModel inventoryModel = restTemplate.getForObject(url, InventoryModel.class);

            return inventoryModel;

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
