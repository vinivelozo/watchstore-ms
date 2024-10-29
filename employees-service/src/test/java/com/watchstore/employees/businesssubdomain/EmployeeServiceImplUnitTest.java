package com.watchstore.employees.businesssubdomain;

import com.watchstore.employees.dataaccesslayer.*;
import com.watchstore.employees.presentationlayer.EmployeeController;
import com.watchstore.employees.presentationlayer.EmployeeResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
class EmployeeServiceImplUnitTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;


    @Test
    public void whenEmployeesExists_thenReturnEmptyList(){

        //arrange
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        //act
        List<EmployeeResponseModel> responseEntity = employeeServiceImpl.getAllEmployees();

        //assert
        assertNotNull(responseEntity);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void whenEmployeeExists_thenReturnEmployeeId(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        PhoneNumber phoneNumber = new PhoneNumber(PhoneType.HOME, "5145695157");
        phoneNumbers.add(phoneNumber);
       Employee employee = new Employee(new Address("340 rue d emeraude", "Candiac", "Quebec", "Canada", "J5R0S1"),
                phoneNumbers, "Vinicius", "Velozo", "vinivelozo01@outlook.com", "Manager", 3400.0, Status.ACTIVE);
        //arrange
        when(employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId("e5913a79-9b1e-4516-9ffd-06578e7af201")).thenReturn(employee);

        //act
        EmployeeResponseModel employeeReturn = employeeServiceImpl.getEmployeeById("e5913a79-9b1e-4516-9ffd-06578e7af201");

        //assert
        assertEquals(employeeReturn.getFirstName(), employee.getFirstName());
        assertEquals(employeeReturn.getLastName(), employee.getLastName());
        assertEquals(employeeReturn.getEmail(), employee.getEmail());
        assertEquals(employeeReturn.getAddress(), employee.getAddress());
        assertEquals(employeeReturn.getSalary(), employee.getSalary());
        assertEquals(employeeReturn.getJob_title(), employee.getJob_title());
        assertEquals(employeeReturn.getPhoneNumbers(), employee.getPhoneNumbers());
        assertNotNull(employee);
        assertNotNull(employee.getFirstName());
        //do for status
        verify(employeeRepository, times(1)).findEmployeeByEmployeeIdentifier_EmployeeId("e5913a79-9b1e-4516-9ffd-06578e7af201");

    }

    // create employee request model and then compare with the response model that i will receive and I will act



}