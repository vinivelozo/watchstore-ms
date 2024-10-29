package com.watchstore.sales.domainclientlayer.stores;

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

    public StoreModel getStoreByStoreId(String storeId) {
        try {
            String url = STORES_SERVICE_BASE_URL + "/" + storeId;

            StoreModel storeModel = restTemplate.getForObject(url, StoreModel.class);

            return storeModel;

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
