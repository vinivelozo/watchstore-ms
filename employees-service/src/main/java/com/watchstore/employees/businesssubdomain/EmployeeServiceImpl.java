package com.watchstore.employees.businesssubdomain;

import com.watchstore.employees.dataaccesslayer.Employee;
import com.watchstore.employees.dataaccesslayer.EmployeeIdentifier;
import com.watchstore.employees.dataaccesslayer.EmployeeRepository;
import com.watchstore.employees.mapperlayer.EmployeeRequestMapper;
import com.watchstore.employees.mapperlayer.EmployeeResponseMapper;
import com.watchstore.employees.presentationlayer.EmployeeRequestModel;
import com.watchstore.employees.presentationlayer.EmployeeResponseModel;
import com.watchstore.employees.utils.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeRequestMapper employeeRequestMapper;
    private EmployeeResponseMapper employeeResponseMapper;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeRequestMapper employeeRequestMapper, EmployeeResponseMapper employeeResponseMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeRequestMapper = employeeRequestMapper;
        this.employeeResponseMapper = employeeResponseMapper;
    }

    @Override
    public List<EmployeeResponseModel> getAllEmployees(){

        List<Employee> employeeList = employeeRepository.findAll();

        return employeeResponseMapper.entityListToResponseModelList(employeeList);
    }

    @Override
    public EmployeeResponseModel getEmployeeById(String employeeId) {

        Employee foundEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);
        if(foundEmployee == null){
            throw new NotFoundException("Unknown employee:" + employeeId);
        }
//        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
//        BeanUtils.copyProperties(foundEmployee, employeeResponseModel);
//        employeeResponseModel.setEmployeeId(foundEmployee.getEmployeeIdentifier().getEmployeeId());

        return employeeResponseMapper.entityToResponseModel(foundEmployee);
    }

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel){


        Employee employee = employeeRequestMapper.requestModelToEntity(employeeRequestModel,
                new EmployeeIdentifier());

        return employeeResponseMapper.entityToResponseModel(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, UUID EmployeeId) {
        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(EmployeeId.toString());
        if (existingEmployee == null){
            throw new NotFoundException("Cannot update id: " + EmployeeId);
        }

        Employee employee = employeeRequestMapper.requestModelToEntity(employeeRequestModel,
                existingEmployee.getEmployeeIdentifier());
        employee.setId(existingEmployee.getId());


        return employeeResponseMapper.entityToResponseModel(employeeRepository.save(employee));
    }


    @Override
    public void deleteEmployee(UUID EmployeeId) {
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(EmployeeId.toString());
        if (employee == null) {
            throw new NotFoundException("No employee found with ID: " + EmployeeId);
        }
        employeeRepository.delete(employee);
    }

}
