package com.employee.services;

import com.employee.exceptions.EmployeeNotFoundException;
import com.employee.models.Employee;
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
            return repository.save(employee);

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



    public Mono<Page<Employee>> getPaginatedEmployees(PageRequest pageRequest) {
        return repository.findAllBy(pageRequest.withSort(Sort.by("firstName").descending()))
                .collectList()
                .zipWith(repository.count())
                .map(t-> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    @Override
    public Mono<Void> deleteEmployee(Long id) {
        return repository.deleteById(id);
    }


}
