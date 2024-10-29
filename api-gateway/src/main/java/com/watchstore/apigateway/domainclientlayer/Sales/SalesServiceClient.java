package com.watchstore.apigateway.domainclientlayer.Sales;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.watchstore.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import com.watchstore.apigateway.presentationlayer.Sales.SaleRequestModel;
import com.watchstore.apigateway.presentationlayer.Sales.SaleResponseModel;
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
public class SalesServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String SALES_SERVICE_BASE_URL;

    private SalesServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                   @Value("${app.sales-service.host}") String salesServiceHost,
                                   @Value("${app.sales-service.port}") String salesServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        SALES_SERVICE_BASE_URL = "HTTP://" + salesServiceHost + ":" + salesServicePort + "/api/v1/employees";
    }

    public List<SaleResponseModel> getAllPurchaseForEmployee(String employeeId) {
        try {
            String url = SALES_SERVICE_BASE_URL + "/" + employeeId + "/" + "purchases";

            SaleResponseModel[] saleResponseModels= restTemplate.getForObject(url,SaleResponseModel[].class);

            return Arrays.asList(saleResponseModels);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public SaleResponseModel getEmployeePurchaseBySaleId(String employeeId, String saleId) {
        try {
            String url = SALES_SERVICE_BASE_URL + "/" + employeeId + "/purchases/" + saleId;

            SaleResponseModel saleResponseModel = restTemplate.getForObject(url,SaleResponseModel.class);

            return saleResponseModel;


        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public SaleResponseModel addEmployeePurchase (SaleRequestModel saleRequestModel, String employeeId) {
        try {
            String url = SALES_SERVICE_BASE_URL + "/" + employeeId + "/purchases";
            return restTemplate.postForObject(url, saleRequestModel, SaleResponseModel.class);

//            SaleResponseModel saleResponseModel = restTemplate.postForObject(url, saleRequestModel, SaleResponseModel.class);
//
//            return saleResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public SaleResponseModel updateEmployeeSale(SaleRequestModel saleRequestModel, String employeeId, String saleId) {
        try {
            String url = SALES_SERVICE_BASE_URL + "/" + employeeId + "/purchases/" + saleId;

            // Create an HttpEntity containing the request body
            HttpEntity<SaleRequestModel> requestEntity = new HttpEntity<>(saleRequestModel);
            restTemplate.put(url, saleRequestModel);
            return getEmployeePurchaseBySaleId(employeeId, saleId);

//            // Perform a PUT request using RestTemplate.exchange()
//            ResponseEntity<SaleResponseModel> responseEntity = restTemplate.exchange(
//                    url,
//                    HttpMethod.PUT,
//                    requestEntity,
//                    SaleResponseModel.class
//            );


//            return responseEntity.getBody();

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deletePurchaseFromEmployee(String employeeId, String saleId) {
        try {
            String url = SALES_SERVICE_BASE_URL + "/" + employeeId + "/purchases/" + saleId;

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
