package com.employee.services;

import com.employee.exceptions.DepartmentNotFoundException;
import com.employee.exceptions.EmployeeNotFoundException;
import com.employee.models.Employee;
import com.employee.repositories.DepartmentRepository;
import com.employee.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;

    public Flux<Employee> getAllEmployees(){
        return repository.findAll();
    }

    public Mono<Employee> findEmployeeById(Long id) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException("Employee not found with ID: " + id)));
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        try{

            return departmentRepository.findById(employee.getDepartmentId())
                    .switchIfEmpty(Mono.error(new DepartmentNotFoundException("Department does not exists.")))
                    .flatMap(dept -> repository.save(employee));

        }catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Employee exists with the email or phone number");
        }

    }

    @Override
    public Mono<Employee> editEmployee(Long id, Employee employee) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException("Employee not found with ID: " + id)))
                .flatMap(existingEmployee -> {
                    // Update the existingEmployee with the new data
                    existingEmployee.setId(id);
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    existingEmployee.setPhone(employee.getPhone());
                    existingEmployee.setDepartmentId(employee.getDepartmentId());

                    return repository.save(existingEmployee);
                });
    }



    public Mono<Page<Employee>> getPaginatedEmployees(int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Order.asc("id")); // Define the sorting criteria
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);


        return repository.findAll(pageRequest)
                .collectList()
                .flatMap(employeeList -> {
                    return repository.count()  // Count all records
                            .map(totalCount -> new PageImpl<>(employeeList, pageRequest, totalCount));
                });

    }

    @Override
    public Mono<Void> deleteEmployee(Long id) {
        return repository.deleteById(id);
    }










}
