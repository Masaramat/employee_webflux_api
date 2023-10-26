package com.employee.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.util.List;

@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{
    @Id
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "Email format not correct")
    private String email;
    @NotBlank(message = "Phone Number is required")
    private String phone;
    @NotBlank(message = "password is required")
    private String password;
    private Role role;





}
