package com.employee.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    private Long id;
    @NotBlank(message = "Department name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;


}
