package com.watchstore.apigateway.presentationlayer.Employee;

import com.watchstore.apigateway.domainclientlayer.Employee.Address;
import com.watchstore.apigateway.domainclientlayer.Employee.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EmployeeResponseModel extends RepresentationModel<EmployeeResponseModel> {

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
