package com.watchstore.apigateway.businesslayer.Employee;

import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService {

    List<EmployeeResponseModel> getAllEmployees();

    EmployeeResponseModel getEmployeeByEmployeeId(String employeeId);

    EmployeeResponseModel addEmployee (EmployeeRequestModel employeeRequestModel);

    EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, UUID employeeId);

    void deleteEmployee(UUID employeeId);
}
