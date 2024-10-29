package com.watchstore.employees.mapperlayer;

import com.watchstore.employees.dataaccesslayer.Employee;
import com.watchstore.employees.dataaccesslayer.EmployeeIdentifier;
import com.watchstore.employees.presentationlayer.EmployeeRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface EmployeeRequestMapper {

    @Mapping(target = "id", ignore = true)
    Employee requestModelToEntity(EmployeeRequestModel employeeRequestModel,
                                  EmployeeIdentifier employeeIdentifier);
}
