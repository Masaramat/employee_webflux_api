package com.employee.controllers;

import com.employee.models.Employee;
import com.employee.services.EmployeeService;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Employee> getAllEmployees(){
        return service.getAllEmployees();
    }

    @GetMapping(value = "/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Employee> getEmployeeById(@PathVariable Long id){

        return service.findEmployeeById(id);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Employee> createEmployee(@Valid @RequestBody Employee employee){
        Set<ConstraintViolation<Employee>> violations = validateEmployee(employee);
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return service.save(employee);
    }

    @PutMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Employee> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody Employee employee){
        Set<ConstraintViolation<Employee>> violations = validateEmployee(employee);
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return service.editEmployee(id, employee);
    }

    @GetMapping("employees/pages")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Page<Employee>> getPaginatedEmployees(@RequestParam("page") int page, @RequestParam("size") int size){
        return service.getPaginatedEmployees(page, size);
    }

    @DeleteMapping("employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") Long id){
        return service.deleteEmployee(id);
    }

    private Set<ConstraintViolation<Employee>> validateEmployee(Employee employee) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(employee);
    }



}
