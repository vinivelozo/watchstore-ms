package com.watchstore.employees.mapperlayer;

import com.watchstore.employees.dataaccesslayer.Employee;
import com.watchstore.employees.presentationlayer.EmployeeController;
import com.watchstore.employees.presentationlayer.EmployeeResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
//import org.springframework.hateoas.Link;

import java.util.List;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    @Mapping(expression = "java(employee.getEmployeeIdentifier().getEmployeeId())", target = "employeeId")
    EmployeeResponseModel entityToResponseModel(Employee employee);

    List<EmployeeResponseModel> entityListToResponseModelList(List<Employee> employeeList);

//    @AfterMapping
//    default void addLinks(@MappingTarget EmployeeResponseModel model, Employee employee){
//        //Self link
//        Link selfLink = linkTo(methodOn(EmployeeController.class)
//                .getEmployeeById(model.getEmployeeId()))
//                .withRel("Employee by id");
//        model.add(selfLink);
//
//        // All inventories
//        Link employeesLink =
//                linkTo(methodOn(EmployeeController.class)
//                        .getAllEmployees())
//                        .withRel("All employees");
//        model.add(employeesLink);
//
//    }
}
