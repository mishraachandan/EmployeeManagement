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

    @Size(min = 4, max = 15, message = "The firstName must be in range of 4 to 15 characters")
    private String firstName;

    @Size(min = 4, max = 15, message = "The Lastname must be in range of 4 to 15 characters")
    private String lastName;

    @Email(message = "Please Enter the correct emailId")
    private String email;


}
