package com.watchstore.apigateway.mappinglayer;

import com.watchstore.apigateway.presentationlayer.Employee.EmployeeController;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EmployeeResponseMapper {

    EmployeeResponseModel responseModelToResponseModel(EmployeeResponseModel employeeResponseModel);

    List<EmployeeResponseModel> responseModelListToResponseModelList(List<EmployeeResponseModel> employeeResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget EmployeeResponseModel employeeResponseModel) {

        //Link
        Link allLink = linkTo(methodOn(EmployeeController.class)
                .getAllEmployees())
                .withRel("All employees");
        employeeResponseModel.add(allLink);

        //Link
        Link selfLink = linkTo(methodOn(EmployeeController.class)
                .getEmployeeByEmployeeId(employeeResponseModel.getEmployeeId()))
                .withRel("employee by id");
        employeeResponseModel.add(selfLink);
    }

}
