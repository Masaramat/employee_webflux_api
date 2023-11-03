package com.employee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format invalid")
    private String email;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotBlank(message = "Phone Number cannot be empty")
    private String phone;

    private Long departmentId;
}
