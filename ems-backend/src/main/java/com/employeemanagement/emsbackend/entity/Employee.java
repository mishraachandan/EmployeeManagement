package com.employeemanagement.emsbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "first_name") // default name will be firstName this is a explicit way to naming the column.
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
}
