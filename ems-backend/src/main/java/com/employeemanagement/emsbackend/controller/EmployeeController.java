package com.employeemanagement.emsbackend.controller;

import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;
import com.employeemanagement.emsbackend.service.EmployeeService;
import com.employeemanagement.emsbackend.serviceImpl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    //Build Add employee REST API

    @PostMapping()
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmp = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long empId) throws ResourceNotFoundException {
        EmployeeDto employeeDto = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);

    }

    @GetMapping(value = "/allEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
//        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping(value = "/updateEmp")
    public ResponseEntity<EmployeeDto> updateEmp(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.CREATED);
    }


}
