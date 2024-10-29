package com.watchstore.employees.dataaccesslayer;

import com.watchstore.employees.presentationlayer.EmployeeRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setDb() {
        employeeRepository.deleteAll();
    }

    @Test
    public void whenEmployeeExists_ReturnEmployeeByEmployeeId(){
        //arrange
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);
        //act
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employee1.getEmployeeIdentifier().getEmployeeId());

        //assert
        assertNotNull(employee);
        assertEquals(employee.getEmployeeIdentifier(), employee1.getEmployeeIdentifier());
        assertEquals(employee.getFirstName(), employee1.getFirstName());
        assertEquals(employee.getLastName(), employee1.getLastName());
    }

    @Test
    public void whenEmployeeDoesNotExist_ReturnNull(){
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);

        //act
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId("e5913a79-9b1e-4516-9ffd-06578e7a0000");

        //assert
        assertNull(employee);

    }

    @Test
    public void whenEmployeeIdIsInvalid_ReturnNull(){
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);

        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId("e5913a79-9b1e-4516-9ffd");

        //assert
        assertNull(employee);
    }

    @Test
    public void whenEmployeeIdIsNull_ReturnNull(){
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);

        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(null);

        //assert
        assertNull(employee);

    }

    @Test
    public void whenEmployeeIdIsEmpty_ReturnNull(){
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);

        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId("");

        //assert
        assertNull(employee);
    }

    @Test
    public void whenMemberIdIsBlank_ReturnNull(){
        Address address = new Address("123 rue St Paul", "City", "State", "Country", "12345");
        List<PhoneNumber> phoneNumbers = Arrays.asList(new PhoneNumber(PhoneType.MOBILE, "1234567890"));

        Employee employee1 = new Employee(address,phoneNumbers,"Vini", "Sousa", "vinihello@outlook.com", "Manager", 3000.00, Status.ACTIVE);

        employeeRepository.save(employee1);

        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(" ");

        //assert
        assertNull(employee);
    }
}