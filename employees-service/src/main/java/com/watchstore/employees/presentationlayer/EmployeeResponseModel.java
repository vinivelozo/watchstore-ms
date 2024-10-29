package com.watchstore.employees.presentationlayer;

import com.watchstore.employees.dataaccesslayer.Address;
import com.watchstore.employees.dataaccesslayer.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.hateoas.RepresentationModel;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseModel  {

   // private Integer id;

    private String employeeId;
    //private EmployeeIdentifier employeeIdentifier;


    private Address address;

    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String job_title;
    private String status;

    List<PhoneNumber> phoneNumbers;
}
