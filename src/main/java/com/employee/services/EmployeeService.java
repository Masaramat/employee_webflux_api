package com.employee.services;

import com.employee.models.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    public Flux<Employee> getAllEmployees();
    public Mono<Employee> findEmployeeById(Long id);

    public Mono<Employee> save(Employee employee);

    public Mono<Employee> editEmployee(Long id, Employee employee);
}
