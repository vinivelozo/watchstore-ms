package com.watchstore.apigateway.domainclientlayer.Employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
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
public class EmployeesServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String EMPLOYEES_SERVICE_BASE_URL;

    private EmployeesServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                   @Value("${app.employees-service.host}") String employeesServiceHost,
                                   @Value("${app.employees-service.port}") String employeesServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        EMPLOYEES_SERVICE_BASE_URL = "HTTP://" + employeesServiceHost + ":" + employeesServicePort + "/api/v1/employees";


    }

    public List<EmployeeResponseModel> getAllEmployees(){
        try{
            String url = EMPLOYEES_SERVICE_BASE_URL;

            EmployeeResponseModel[] employeeResponseModels = restTemplate.getForObject(url, EmployeeResponseModel[].class);
            return Arrays.asList(employeeResponseModels);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        try {
            String url = EMPLOYEES_SERVICE_BASE_URL + "/" + employeeId;

            EmployeeResponseModel employeeResponseModel = restTemplate.getForObject(url, EmployeeResponseModel.class);

            return employeeResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }


    }

    public EmployeeResponseModel addEmployee (EmployeeRequestModel employeeRequestModel) {
        try {
            String url = EMPLOYEES_SERVICE_BASE_URL;

            EmployeeResponseModel employeeResponseModel = restTemplate.postForObject(url, employeeRequestModel, EmployeeResponseModel.class);

            return employeeResponseModel;

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, UUID employeeId) {
        try {
            String url = EMPLOYEES_SERVICE_BASE_URL + "/" + employeeId;

            // Create an HttpEntity containing the request body
            HttpEntity<EmployeeRequestModel> requestEntity = new HttpEntity<>(employeeRequestModel);

            // Perform a PUT request using RestTemplate.exchange()
            ResponseEntity<EmployeeResponseModel> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    EmployeeResponseModel.class
            );

            // Extract and return the EmployeeResponseModel from the response entity
            return responseEntity.getBody();

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteEmployee(UUID employeeId) {
        try {
            String url = EMPLOYEES_SERVICE_BASE_URL + "/" + employeeId;

            // Perform DELETE request
            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


        private RuntimeException handleHttpClientException (HttpClientErrorException ex){
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
        private String getErrorMessage (HttpClientErrorException ex){
            try {
                return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
            } catch (IOException ioex) {
                return ioex.getMessage();
            }
        }
    }


