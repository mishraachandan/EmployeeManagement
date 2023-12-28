package com.employeemanagement.emsbackend.controller;

import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.exception.EmailAlreadyExistException;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;
import com.employeemanagement.emsbackend.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {


    private final EmployeeService employeeService;
    private final Environment environment;

    @Autowired
    public EmployeeController(EmployeeService employeeService, Environment environment){
        this.employeeService = employeeService;
        this.environment = environment;
    }

    //Build Add employee REST API

    @PostMapping("/createEmp")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) throws EmailAlreadyExistException {
        String savedEmp = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") @Min(value = 4, message = "The employee id must be greater than 3") Long empId) throws ResourceNotFoundException {
        EmployeeDto employeeDto = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);

    }

    @GetMapping(value = "/allEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
//        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping(value = "/updateEmp")
    public ResponseEntity<EmployeeDto> updateEmp(@RequestBody EmployeeDto employeeDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeDto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteEmp/{id}")
    public ResponseEntity<String> deleteEmp(@PathVariable("id")  long id) throws ResourceNotFoundException {
        try{
            return new ResponseEntity<>(employeeService.deleteEmp(id), HttpStatus.OK);
        }
        catch (Exception e){
            String message = e.getMessage();
            String errorMessage = environment.getProperty(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage != null ? errorMessage : e.getMessage(), e);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
        }
    }


}
