package com.employeemanagement.emsbackend.service;


import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    // Get this method working after the use of this.
//    default String giveDetails(Long employeeId){
//    };

    EmployeeDto getEmployeeById(long empId) throws ResourceNotFoundException;

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto) throws ResourceNotFoundException;

    String deleteEmp(long id) throws ResourceNotFoundException;

}
