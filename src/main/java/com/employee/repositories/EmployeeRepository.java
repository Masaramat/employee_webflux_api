package com.employee.repositories;

import com.employee.models.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long>, ReactiveSortingRepository<Employee, Long> {

    Flux<Employee> findAllBy(Pageable pageable);
}
