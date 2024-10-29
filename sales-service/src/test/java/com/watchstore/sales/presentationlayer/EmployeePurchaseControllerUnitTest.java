package com.watchstore.sales.presentationlayer;

import com.watchstore.sales.businesslayer.SaleService;
import com.watchstore.sales.utils.exceptions.InvalidInputException;
import com.watchstore.sales.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EmployeePurchaseController.class)
class EmployeePurchaseControllerUnitTest {


    @Autowired
    EmployeePurchaseController employeePurchaseController;

    @MockBean
    private SaleService saleService;

    private final String VALID_EMPLOYEE_ID = "valid-employee-id";
    private final String VALID_SALE_ID = "valid-sale-id";
    private final String INVALID_EMPLOYEE_ID = "invalid-employee-id";
    private final String INVALID_SALE_ID = "invalid-sale-id";

    @Test
    void getAllPurchaseForEmployee_ValidEmployeeId_ReturnsOk() {
        // Arrange
        List<SaleResponseModel> sales = Collections.singletonList(new SaleResponseModel());
        when(saleService.getAllPurchaseForEmployee(VALID_EMPLOYEE_ID)).thenReturn(sales);

        // Act
        ResponseEntity<List<SaleResponseModel>> response = employeePurchaseController.getAllPurchaseForEmployee(VALID_EMPLOYEE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sales, response.getBody());
    }

    @Test
    void getAllPurchaseForEmployee_InvalidEmployeeId_ThrowsInvalidInputException() {
        // Arrange
        when(saleService.getAllPurchaseForEmployee(INVALID_EMPLOYEE_ID)).thenThrow(new InvalidInputException("EmployeeId provided is invalid " + INVALID_EMPLOYEE_ID));

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            employeePurchaseController.getAllPurchaseForEmployee(INVALID_EMPLOYEE_ID);
        });

        // Verify
        assertEquals("EmployeeId provided is invalid " + INVALID_EMPLOYEE_ID, exception.getMessage());
    }

    @Test
    void getEmployeePurchaseBySaleId_ValidIds_ReturnsOk() {
        // Arrange
        SaleResponseModel saleResponse = new SaleResponseModel();
        when(saleService.getEmployeePurchaseBySaleId(VALID_EMPLOYEE_ID, VALID_SALE_ID)).thenReturn(saleResponse);

        // Act
        ResponseEntity<SaleResponseModel> response = employeePurchaseController.getEmployeePurchaseBySaleId(VALID_EMPLOYEE_ID, VALID_SALE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saleResponse, response.getBody());
    }

    @Test
    void getEmployeePurchaseBySaleId_InvalidSaleId_ThrowsNotFoundException() {
        // Arrange
        when(saleService.getEmployeePurchaseBySaleId(VALID_EMPLOYEE_ID, INVALID_SALE_ID)).thenThrow(new NotFoundException("Unknown saleId provided: " + INVALID_SALE_ID));

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeePurchaseController.getEmployeePurchaseBySaleId(VALID_EMPLOYEE_ID, INVALID_SALE_ID);
        });

        // Verify
        assertEquals("Unknown saleId provided: " + INVALID_SALE_ID, exception.getMessage());
    }

    @Test
    void addEmployeePurchase_ValidRequest_ReturnsOk() {
        // Arrange
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        SaleResponseModel saleResponse = new SaleResponseModel();
        when(saleService.addEmployeePurchase(saleRequestModel, VALID_EMPLOYEE_ID)).thenReturn(saleResponse);

        // Act
        ResponseEntity<SaleResponseModel> response = employeePurchaseController.addEmployeePurchase(saleRequestModel, VALID_EMPLOYEE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saleResponse, response.getBody());
    }

    @Test
    void addEmployeePurchase_InvalidEmployeeId_ThrowsInvalidInputException() {
        // Arrange
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        when(saleService.addEmployeePurchase(saleRequestModel, INVALID_EMPLOYEE_ID)).thenThrow(new InvalidInputException("employeeId provided is invalid " + INVALID_EMPLOYEE_ID));

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            employeePurchaseController.addEmployeePurchase(saleRequestModel, INVALID_EMPLOYEE_ID);
        });

        // Verify
        assertEquals("employeeId provided is invalid " + INVALID_EMPLOYEE_ID, exception.getMessage());
    }

    @Test
    void updateEmployeeSale_ValidRequest_ReturnsOk() {
        // Arrange
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        SaleResponseModel saleResponse = new SaleResponseModel();
        when(saleService.updateEmployeeSale(saleRequestModel, VALID_EMPLOYEE_ID, VALID_SALE_ID)).thenReturn(saleResponse);

        // Act
        ResponseEntity<SaleResponseModel> response = employeePurchaseController.updateEmployeeSale(saleRequestModel, VALID_EMPLOYEE_ID, VALID_SALE_ID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saleResponse, response.getBody());
    }

    @Test
    void updateEmployeeSale_InvalidSaleId_ThrowsNotFoundException() {
        // Arrange
        SaleRequestModel saleRequestModel = new SaleRequestModel();
        when(saleService.updateEmployeeSale(saleRequestModel, VALID_EMPLOYEE_ID, INVALID_SALE_ID)).thenThrow(new NotFoundException("SaleId provided is unknown " + INVALID_SALE_ID));

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeePurchaseController.updateEmployeeSale(saleRequestModel, VALID_EMPLOYEE_ID, INVALID_SALE_ID);
        });

        // Verify
        assertEquals("SaleId provided is unknown " + INVALID_SALE_ID, exception.getMessage());
    }

    @Test
    void deletePurchaseFromEmployee_ValidRequest_ReturnsNoContent() {
        // Arrange
        doNothing().when(saleService).deletePurchaseFromEmployee(VALID_EMPLOYEE_ID, VALID_SALE_ID);

        // Act
        ResponseEntity<Void> response = employeePurchaseController.deletePurchaseFromEmployee(VALID_EMPLOYEE_ID, VALID_SALE_ID);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deletePurchaseFromEmployee_InvalidSaleId_ThrowsNotFoundException() {
        // Arrange
        doThrow(new NotFoundException("Unknow saleId" + INVALID_SALE_ID)).when(saleService).deletePurchaseFromEmployee(VALID_EMPLOYEE_ID, INVALID_SALE_ID);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            employeePurchaseController.deletePurchaseFromEmployee(VALID_EMPLOYEE_ID, INVALID_SALE_ID);
        });

        // Verify
        assertEquals("Unknow saleId" + INVALID_SALE_ID, exception.getMessage());
    }
}

