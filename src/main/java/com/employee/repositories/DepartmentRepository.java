package com.employee.repositories;

import com.employee.models.Department;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface DepartmentRepository extends R2dbcRepository<Department, Long> {
}
