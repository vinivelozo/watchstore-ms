package com.watchstore.employees.businesssubdomain;

import com.watchstore.employees.presentationlayer.EmployeeRequestModel;
import com.watchstore.employees.presentationlayer.EmployeeResponseModel;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeResponseModel> getAllEmployees();
    EmployeeResponseModel getEmployeeById(String employeeId);
    EmployeeResponseModel addEmployee (EmployeeRequestModel employeeRequestModel);

    EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, UUID EmployeeId);

    void deleteEmployee(UUID EmployeeId);
}
