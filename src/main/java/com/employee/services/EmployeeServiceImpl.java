package com.employee.services;

import com.employee.models.Employee;
import com.employee.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository repository;

    public Flux<Employee> getAllEmployees(){
        return repository.findAll();
    }

    public Mono<Employee> findEmployeeById(Long id) {

        return repository.findById(id);
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        try{
            return repository.save(employee);

        }catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getMessage());
        }

    }

    @Override
    public Mono<Employee> editEmployee(Long id, Employee employee) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new FileNotFoundException("Employee not found with ID: " + id)))
                .flatMap(existingEmployee -> {
                    // Update the existingEmployee with the new data
                    existingEmployee.setId(id);
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    existingEmployee.setPhone(employee.getPhone());
                    existingEmployee.setDepartment(employee.getDepartment());

                    return repository.save(existingEmployee);
                });
    }



}
