package com.watchstore.apigateway.businesslayer.Employee;

import com.watchstore.apigateway.domainclientlayer.Employee.EmployeesServiceClient;
import com.watchstore.apigateway.mappinglayer.EmployeeResponseMapper;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeRequestModel;
import com.watchstore.apigateway.presentationlayer.Employee.EmployeeResponseModel;
import com.watchstore.apigateway.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeesServiceClient employeesServiceClient;

    private final EmployeeResponseMapper employeeResponseMapper;


    public EmployeeServiceImpl(EmployeesServiceClient employeesServiceClient, EmployeeResponseMapper employeeResponseMapper) {
        this.employeesServiceClient = employeesServiceClient;
        this.employeeResponseMapper = employeeResponseMapper;
    }

    @Override
    public List<EmployeeResponseModel> getAllEmployees(){

        return employeeResponseMapper.responseModelListToResponseModelList(employeesServiceClient.getAllEmployees());
    }

    @Override
    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId) {
        return employeeResponseMapper.responseModelToResponseModel(employeesServiceClient.getEmployeeByEmployeeId(employeeId));

    }

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        return employeeResponseMapper.responseModelToResponseModel(employeesServiceClient.addEmployee(employeeRequestModel));
    }

    @Override
    public EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, UUID employeeId) {
        return employeeResponseMapper.responseModelToResponseModel(employeesServiceClient.updateEmployee(employeeRequestModel, employeeId));
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        employeesServiceClient.deleteEmployee(employeeId);
    }




}


