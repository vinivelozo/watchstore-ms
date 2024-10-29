package com.watchstore.employees.presentationlayer;

import com.watchstore.employees.dataaccesslayer.*;
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
class EmployeeControllerIntegrationTest {
    private final String BASE_URI_EMPLOYEES = "/api/v1/employees";
    private final String FOUND_EMPLOYEE_ID = "e5913a79-9b1e-4516-9ffd-06578e7af261";

    private final String FOUND_MEMBER_FIRST_NAME = "Vilma";
    private final String FOUND_MEMBER_LAST_NAME = "Chawner";

    private final String NOT_FOUND_EMPLOYEE_ID = "e5913a79-9b1e-4516-9ffd-06578e000000";
    private final String INVALID_EMPLOYEE_ID = "05c8ab76-4f75-45c1-b6e2";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetEmployees_thenReturnAllEmployees() {
        //arrange
        Long sizeDB = employeeRepository.count();

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(EmployeeResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertTrue(list.size() == sizeDB);
                });
    }

    @Test
    public void whenEmployeeIdExists_thenReturnEmployee() {
        //arrange
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(FOUND_EMPLOYEE_ID);

        //act and assert
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((response) -> {
                    assertNotNull(response);
                    assertEquals(response.getEmployeeId().toString(), employee.getEmployeeIdentifier().getEmployeeId());
                    assertEquals(response.getFirstName(), employee.getFirstName());
                    assertEquals(response.getLastName(), employee.getLastName());

                });
    }

    @Test
    public void whenEmployeeDoesNotExists_thenReturnNotFound() {
        webTestClient.get()
                .uri(BASE_URI_EMPLOYEES + "/" + NOT_FOUND_EMPLOYEE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Unknown employee:" + NOT_FOUND_EMPLOYEE_ID);

    }

    @Test
    public void whenValidEmployee_thenCreateMember() {
        //arrange
        long sizeDB = employeeRepository.count();
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.HOME, "5145695157");
        phoneNumbers.add(phoneNumber);

        // Create an Employee object
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "Vinicius", "Velozo",
                "vinisousa@outlook.com", 1000.00, "Manager", "ACTIVE", phoneNumbers
        );

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((employeeResponseModel) -> {
                    assertNotNull(employeeResponseModel);
                    assertEquals(employeeResponseModel.getFirstName(), employeeRequestModel.getFirstName());
                    assertEquals(employeeResponseModel.getLastName(), employeeRequestModel.getLastName());
                    assertEquals(employeeResponseModel.getEmail(), employeeRequestModel.getEmail());
                    assertEquals(employeeResponseModel.getAddress().getStreet(), employeeRequestModel.getAddress().getStreet());
                    assertEquals(employeeResponseModel.getAddress().getCity(), employeeRequestModel.getAddress().getCity());
                    assertEquals(employeeResponseModel.getAddress().getProvince(), employeeRequestModel.getAddress().getProvince());
                    assertEquals(employeeResponseModel.getAddress().getCountry(), employeeRequestModel.getAddress().getCountry());
                    assertEquals(employeeResponseModel.getAddress().getPostal_code(), employeeRequestModel.getAddress().getPostal_code());
                    assertEquals(employeeResponseModel.getPhoneNumbers().size(), employeeRequestModel.getPhoneNumbers().size());


                });

        long sizeDBAfter = employeeRepository.count();
        assertEquals(sizeDB + 1, sizeDBAfter);


    }

    @Test
    public void whenEmployeeExists_thenUpdateEmployee() {
        //arrange
        long sizeDB = employeeRepository.count();
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(FOUND_EMPLOYEE_ID);

        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.HOME, "5145695157");
        phoneNumbers.add(phoneNumber);

        // Create an Employee object
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "Vinicius", "Velozo",
                "vinisousa@outlook.com", 1000.00, "Manager", "ACTIVE", phoneNumbers
        );

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(EmployeeResponseModel.class)
                .value((employeeResponseModel) -> {
                    assertNotNull(employeeResponseModel);
                    assertEquals(employeeResponseModel.getFirstName(), employeeRequestModel.getFirstName());
                    assertEquals(employeeResponseModel.getLastName(), employeeRequestModel.getLastName());
                    assertEquals(employeeResponseModel.getEmail(), employeeRequestModel.getEmail());
                    assertEquals(employeeResponseModel.getAddress().getStreet(), employeeRequestModel.getAddress().getStreet());
                    assertEquals(employeeResponseModel.getAddress().getCity(), employeeRequestModel.getAddress().getCity());
                    assertEquals(employeeResponseModel.getAddress().getProvince(), employeeRequestModel.getAddress().getProvince());
                    assertEquals(employeeResponseModel.getAddress().getCountry(), employeeRequestModel.getAddress().getCountry());
                    assertEquals(employeeResponseModel.getAddress().getPostal_code(), employeeRequestModel.getAddress().getPostal_code());
                    assertEquals(employeeResponseModel.getPhoneNumbers().size(), employeeRequestModel.getPhoneNumbers().size());

                });

        long sizeDBAfter = employeeRepository.count();
        assertEquals(sizeDB, sizeDBAfter);

    }

    @Test
    public void whenUpdateMemberDoesNotExist_thenThrowException(){
        //arrange
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.HOME, "5145695157");
        phoneNumbers.add(phoneNumber);

        // Create an Employee object
        EmployeeRequestModel employeeRequestModel = new EmployeeRequestModel(
                new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"), "Vinicius", "Velozo",
                "vinisousa@outlook.com", 1000.00, "Manager", "ACTIVE", phoneNumbers
        );

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_EMPLOYEES + "/" + NOT_FOUND_EMPLOYEE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employeeRequestModel)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("Cannot update id: " + NOT_FOUND_EMPLOYEE_ID);

    }

    @Test
    public void whenEmployeeExists_thenDeleteEmployee() {
        //arrange
        long sizeDB = employeeRepository.count();

        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_EMPLOYEES + "/" + FOUND_EMPLOYEE_ID)
                .exchange()
                .expectStatus().isNoContent();

        long sizeDBAfter = employeeRepository.count();
        assertEquals(sizeDB - 1, sizeDBAfter);

    }

    @Test
    public void whenDeleteEmployeeDoesNotExist_thenReturnException(){
        //act and assert
        webTestClient.delete()
                .uri(BASE_URI_EMPLOYEES + "/" + NOT_FOUND_EMPLOYEE_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.httpStatus").isEqualTo("NOT_FOUND")
                .jsonPath("$.message").isEqualTo("No employee found with ID: " + NOT_FOUND_EMPLOYEE_ID);


    }

}