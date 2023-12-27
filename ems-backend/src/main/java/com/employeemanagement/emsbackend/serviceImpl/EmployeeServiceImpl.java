package com.employeemanagement.emsbackend.serviceImpl;

import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.entity.Employee;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;
import com.employeemanagement.emsbackend.mapper.EmployeeMapper;
import com.employeemanagement.emsbackend.repository.EmployeeRepository;
import com.employeemanagement.emsbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(long empId){

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Employee is not existing with employee Id:: +" + empId
        ));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map((EmployeeMapper::mapToEmployeeDto)).toList();
    }
    
    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto){
        
        // get the employee by ID;
        Employee employee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Please check again as the employeeId provided is incorrect"));
        
        // perform the update operation
        Employee updatedEmp = setUpdated(employeeDto, employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmp);
    }

    private Employee setUpdated(EmployeeDto employeeDto, Employee employee) {
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public String deleteEmp(long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sorry Please enter the correct Emp Id")
        );
        employeeRepository.delete(employee);
        return "Employee with employeeID :: " + id + " got deleted from the database.";

    }


}
