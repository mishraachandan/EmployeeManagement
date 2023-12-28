package com.employeemanagement.emsbackend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {

    private Long id;

    @Size(min = 4, max = 15, message = "{employee.firstname.invalid}")
    private String firstName;

    @Size(min = 4, max = 15, message = "{employee.lastname.invalid}")
    private String lastName;

    @Email(message = "{employee.email.invalid}")
    private String email;

    private String password;


}
