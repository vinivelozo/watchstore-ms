package com.watchstore.employees.presentationlayer;

import com.watchstore.employees.dataaccesslayer.Address;
import com.watchstore.employees.dataaccesslayer.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestModel {

    private Address address;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String job_title;
    private String status;

    List<PhoneNumber> phoneNumbers;
}
