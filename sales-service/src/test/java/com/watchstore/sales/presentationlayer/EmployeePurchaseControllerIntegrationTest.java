package com.watchstore.sales.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchstore.sales.dataaccesslayer.Sale;
import com.watchstore.sales.dataaccesslayer.SaleRepository;
import com.watchstore.sales.domainclientlayer.employees.EmployeeModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment= RANDOM_PORT)
@ActiveProfiles("test")
class EmployeePurchaseControllerIntegrationTest {

//    @Autowired
//    WebTestClient webTestClient;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//
//    @Autowired
//    SaleRepository saleRepository;
//
//
//    private MockRestServiceServer mockRestServiceServer;
//
//
//    private final String EMPLOYEE_BASE_URL = "http://localhost:7001/api/v1/employees";
//
//    private final String SALE_BASE_URL = "api/v1/employees";
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @BeforeEach
//    public void init() {
//
//        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
//    }
//
//    @Test
//    public void whenGetSaleById_thenReturnSale() throws JsonProcessingException, URISyntaxException {
//
//        //arrange
//        var employeeModel = EmployeeModel.builder()
//                .employeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")
//                .build();
//
//        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI(EMPLOYEE_BASE_URL + "/e5913a79-9b1e-4516-9ffd-06578e7af201")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(employeeModel))
//                );
//
//        List<Sale> sales = saleRepository.findSaleByEmployeeModel_EmployeeId("e5913a79-9b1e-4516-9ffd-06578e7af201");
//        Sale sale = sales.stream().filter(s -> s.getEmployeeModel().getEmployeeId().equals("e5913a79-9b1e-4516-9ffd-06578e7af201"))
//                .findFirst()
//                .get();
//
//        assertNotNull(sale);
//
//        String url = SALE_BASE_URL + "/" + employeeModel.getEmployeeId() + "/purchases/" + sale.getSaleIdentifier().getSaleId();
//
//    webTestClient.get()
//            .uri(url)
//            .accept(MediaType.APPLICATION_JSON)
//            .exchange()
//            .expectStatus().isOk()
//            .expectHeader().contentType(MediaType.APPLICATION_JSON)
//            .expectBody(SaleResponseModel.class)
//            .value((response) -> {
//            assertNotNull(response);
//            assertEquals(sale.getSaleIdentifier().getSaleId(), response.getSaleId().toString());
//        });
//        }
//
//
   }

