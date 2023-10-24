package com.employee.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format invalid")
    @Column(value = "email")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    @Column(value = "firstName")
    private String firstName;

    @NotBlank(message = "Name cannot be blank")
    @Column(value = "lastName")
    private String lastName;

    @NotBlank(message = "Phone Number cannot be empty")
    @Column(value = "phone")
    private String phone;


    private Department department;



}
