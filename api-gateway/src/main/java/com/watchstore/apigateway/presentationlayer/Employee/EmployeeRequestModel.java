package com.watchstore.apigateway.presentationlayer.Employee;

import com.watchstore.apigateway.domainclientlayer.Employee.Address;
import com.watchstore.apigateway.domainclientlayer.Employee.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class EmployeeRequestModel {

     Address address;
     String firstName;
     String lastName;
     String email;
     Double salary;
     String job_title;
     String status;

    List<PhoneNumber> phoneNumbers;
}
