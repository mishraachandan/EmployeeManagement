package com.employeemanagement.emsbackend.serviceImpl;

import com.employeemanagement.emsbackend.dto.EmployeeDto;
import com.employeemanagement.emsbackend.entity.Employee;
import com.employeemanagement.emsbackend.exception.EmailAlreadyExistException;
import com.employeemanagement.emsbackend.exception.ResourceNotFoundException;
import com.employeemanagement.emsbackend.mapper.EmployeeMapper;
import com.employeemanagement.emsbackend.repository.EmployeeRepository;
import com.employeemanagement.emsbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String createEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistException {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        List<Employee> employeeList = employeeRepository.findAll();
        boolean emailflag = false;


       for(Employee emp: employeeList){
           if(emp.getEmail().equalsIgnoreCase(employeeDto.getEmail())){
               emailflag = true;
               break;
           }
       }

       if(emailflag){
           throw new EmailAlreadyExistException("SERVICE.EMAIL_EXIST");
       }

        passwordEncoder(employee);
        Employee empSaved = employeeRepository.save(employee);


        return String.format("Congratulations %s%s, your Employee-Id is created.",
                employeeDto.getFirstName().substring(0, 1).toUpperCase(),
                employeeDto.getFirstName().substring(1));
    }

    public void passwordEncoder(Employee employee){
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
    }

    @Override
    public EmployeeDto getEmployeeById(long empId) throws ResourceNotFoundException {

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
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws ResourceNotFoundException {
        
        // get the employee by ID;
        Employee employee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("SERVICE.INVALID_ID"));
        
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
    public String deleteEmp(long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("SERVICE.INVALID_ID")
        );
        employeeRepository.delete(employee);
        return "Employee with employeeID :: " + id + " got deleted from the database.";

    }


}
