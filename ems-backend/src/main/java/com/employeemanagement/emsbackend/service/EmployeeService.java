package com.employeemanagement.emsbackend.service;


import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.entity.Employee;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;
import com.employeemanagement.emsbackend.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    // Get this method working after the use of this.
//    default String giveDetails(Long employeeId){
//    };

    EmployeeDto getEmployeeById(long empId) ;

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    String deleteEmp(long id);

}
