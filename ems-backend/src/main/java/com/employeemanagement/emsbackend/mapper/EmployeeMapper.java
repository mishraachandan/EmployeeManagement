package com.employeemanagement.emsbackend.mapper;

import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.entity.Employee;

public class EmployeeMapper {

    private EmployeeMapper(){}

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPassword());
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail(), employeeDto.getPassword());
    }
}
