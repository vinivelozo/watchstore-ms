package com.watchstore.employees.presentationlayer;

import com.watchstore.employees.businesssubdomain.EmployeeService;
import com.watchstore.employees.dataaccesslayer.*;
import com.watchstore.employees.mapperlayer.EmployeeRequestMapper;
import com.watchstore.employees.utils.GlobalControllerExceptionHandler;
import com.watchstore.employees.utils.HttpErrorInfo;
import com.watchstore.employees.utils.exceptions.InvalidInputException;
import com.watchstore.employees.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EmployeeController.class)
class EmployeeControllerUnitTest {

    @Autowired
    EmployeeController employeeController;

    @MockBean
    EmployeeService employeeService;


    @Test
    public void whenEmployeesExist_thenReturnEmployees(){
        //arrange
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());

        //act
        ResponseEntity<List<EmployeeResponseModel>> responseEntity = employeeController.getAllEmployees();

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(employeeService, times(1)).getAllEmployees();
    }



    @Test
    public void whenEmployeeExists_thenReturnEmployeeById(){
        // Arrange
        String employeeId = "e5913a79-9b1e-4516-9ffd-06578e7a2587";

        // Create a list of phone numbers
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.HOME, "5145695157");
        phoneNumbers.add(phoneNumber);

        // Create an Employee object
        Employee employee = new Employee(
                new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),
                phoneNumbers, "Vinicius", "Velozo", "vinivelozo01@outlook.com", "Manager", 3400.0, Status.ACTIVE);

        // Create an EmployeeResponseModel based on the Employee object
        EmployeeResponseModel expectedEmployee = new EmployeeResponseModel();
        expectedEmployee.setEmployeeId(employeeId);
        expectedEmployee.setAddress(employee.getAddress());
        expectedEmployee.setFirstName(employee.getFirstName());
        expectedEmployee.setLastName(employee.getLastName());
        expectedEmployee.setEmail(employee.getEmail());
        expectedEmployee.setSalary(employee.getSalary());
        expectedEmployee.setJob_title(employee.getJob_title());
        expectedEmployee.setPhoneNumbers(employee.getPhoneNumbers());

        when(employeeService.getEmployeeById(employeeId)).thenReturn(expectedEmployee);

        // Act
        ResponseEntity<EmployeeResponseModel> responseEntity = employeeController.getEmployeeById(employeeId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
        EmployeeResponseModel actualEmployee = responseEntity.getBody();
        assertNotNull(actualEmployee);
        assertEquals(expectedEmployee.getEmployeeId(), actualEmployee.getEmployeeId());
        assertEquals(expectedEmployee.getAddress(), actualEmployee.getAddress());
        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getEmail(), actualEmployee.getEmail());
        assertEquals(expectedEmployee.getSalary(), actualEmployee.getSalary());
        assertEquals(expectedEmployee.getJob_title(), actualEmployee.getJob_title());
        assertEquals(expectedEmployee.getStatus(), actualEmployee.getStatus());
        assertEquals(expectedEmployee.getPhoneNumbers(), actualEmployee.getPhoneNumbers());
    }

    @Test
    public void whenAddingEmployee_thenReturnEmployee() {
        // Arrange
        // Create an EmployeeRequestModel representing the data for the new employee
        Address address = new Address("123 Main St", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));
        EmployeeRequestModel requestModel = EmployeeRequestModel.builder()
                .address(address)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .salary(3000.0)
                .job_title("Manager")
                .status("ACTIVE")
                .phoneNumbers(phoneNumbers)
                .build();

        // Create an EmployeeResponseModel representing the expected response from the service
        EmployeeResponseModel expectedEmployee = new EmployeeResponseModel();
        expectedEmployee.setEmployeeId("e5913a79-9b1e-4516-9ffd-06578e7a2587");
        expectedEmployee.setAddress(requestModel.getAddress());
        expectedEmployee.setFirstName(requestModel.getFirstName());
        expectedEmployee.setLastName(requestModel.getLastName());
        expectedEmployee.setEmail(requestModel.getEmail());
        expectedEmployee.setSalary(requestModel.getSalary());
        expectedEmployee.setJob_title(requestModel.getJob_title());
        expectedEmployee.setStatus(requestModel.getStatus());
        expectedEmployee.setPhoneNumbers(requestModel.getPhoneNumbers());

        // Set expectations for the employeeService mock
        when(employeeService.addEmployee(any(EmployeeRequestModel.class))).thenReturn(expectedEmployee);

        // Act
        ResponseEntity<EmployeeResponseModel> responseEntity = employeeController.addEmployee(requestModel);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(employeeService, times(1)).addEmployee(any(EmployeeRequestModel.class));

        EmployeeResponseModel actualEmployee = responseEntity.getBody();
        assertNotNull(actualEmployee);
        assertEquals(expectedEmployee.getEmployeeId(), actualEmployee.getEmployeeId());
        assertEquals(expectedEmployee.getAddress(), actualEmployee.getAddress());
        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getEmail(), actualEmployee.getEmail());
        assertEquals(expectedEmployee.getSalary(), actualEmployee.getSalary());
        assertEquals(expectedEmployee.getJob_title(), actualEmployee.getJob_title());
        assertEquals(expectedEmployee.getStatus(), actualEmployee.getStatus());
        assertEquals(expectedEmployee.getPhoneNumbers(), actualEmployee.getPhoneNumbers());
    }

    @Test
    public void whenEmployeeExists_thenReturnUpdatedEmployee() {
        // Arrange
        UUID employeeId = UUID.fromString("e5913a79-9b1e-4516-9ffd-06578e7a2587");

        // Create an EmployeeRequestModel representing the updated data for the employee
        EmployeeRequestModel requestModel = new EmployeeRequestModel();
        requestModel.setFirstName("John");
        requestModel.setLastName("Doe");
        // Set other properties as needed

        // Create an EmployeeResponseModel representing the updated employee
        EmployeeResponseModel updatedEmployee = new EmployeeResponseModel();
        updatedEmployee.setEmployeeId(employeeId.toString());
        updatedEmployee.setFirstName(requestModel.getFirstName());
        updatedEmployee.setLastName(requestModel.getLastName());
        // Set other properties as needed

        // Set expectations for the employeeService mock
        when(employeeService.updateEmployee(eq(requestModel),eq(employeeId))).thenReturn(updatedEmployee);

        // Act
        ResponseEntity<EmployeeResponseModel> responseEntity = employeeController.updateEmployee(employeeId, requestModel);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EmployeeResponseModel actualEmployee = responseEntity.getBody();
        assertNotNull(actualEmployee);
        assertEquals(updatedEmployee.getEmployeeId(), actualEmployee.getEmployeeId());
        assertEquals(updatedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(updatedEmployee.getLastName(), actualEmployee.getLastName());
        // Add assertions for other properties as needed
    }


    @Test
    public void whenEmployeeExists_thenDeleteEmployee() {
        // Arrange
        UUID employeeId = UUID.fromString("e5913a79-9b1e-4516-9ffd-06578e7a2587");

        // Mock the behavior of the service method
        doNothing().when(employeeService).deleteEmployee(employeeId);

        // Act
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(employeeId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(employeeId);

        // Assert that NotFoundException is thrown when employee is not found
        doThrow(new NotFoundException("Employee not found")).when(employeeService).deleteEmployee(employeeId);
        assertThrows(NotFoundException.class, () -> {
            employeeController.deleteEmployee(employeeId);
        });
    }

    @Test
    public void testInvalidInputExceptionWithoutMessage() {
        InvalidInputException exception = new InvalidInputException();

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testInvalidInputExceptionWithMessage() {
        String message = "Invalid input";

        InvalidInputException exception = new InvalidInputException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testInvalidInputExceptionWithCause() {
        String message = "Invalid input";
        Throwable cause = new IllegalArgumentException("Cause");

        InvalidInputException exception = new InvalidInputException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testNotFoundExceptionWithoutMessage() {
        NotFoundException exception = new NotFoundException();

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testNotFoundExceptionWithMessage() {
        String message = "Not found";

        NotFoundException exception = new NotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testNotFoundExceptionWithCause() {
        String message = "Not found";
        Throwable cause = new IllegalArgumentException("Cause");

        NotFoundException exception = new NotFoundException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void handleNotFoundException_ReturnsCorrectHttpStatusAndMessage() {
        // Arrange
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Exception exception = new NotFoundException("Employee not found");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

        // Act
        HttpErrorInfo errorInfo = handler.handleNotFoundException(webRequest, exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, errorInfo.getHttpStatus());
        assertEquals("Employee not found", errorInfo.getMessage());
    }

    @Test
    public void handleInvalidInputException_ReturnsCorrectHttpStatusAndMessage() {
        // Arrange
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Exception exception = new InvalidInputException("Invalid input");
        GlobalControllerExceptionHandler handler = new GlobalControllerExceptionHandler();

        // Act
        HttpErrorInfo errorInfo = handler.handleInvalidInputException(webRequest, exception);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, errorInfo.getHttpStatus());
        assertEquals("Invalid input", errorInfo.getMessage());
    }


}
