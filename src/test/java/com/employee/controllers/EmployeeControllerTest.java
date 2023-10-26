package com.employee.controllers;

import com.employee.models.Employee;
import com.employee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(EmployeeController.class)
public class EmployeeControllerTest {


    @MockBean
    private EmployeeService service;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void saveEmployeeTest() {
        Employee employee = Employee.builder()
                .email("innocent@gmail.com")
                .firstName("Innocent")
                .lastName("Mangut")
                .departmentId(1L)
                .phone("090987654323")
                .build();
        when(service.save(employee)).thenReturn(Mono.just(employee));

        String response = webTestClient.post()
                .uri("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();


    }

    @Test
    public void saveEmployeeWithErrorEmailTest() {
        Employee employee = Employee.builder()
                .email("innocent")
                .firstName("Innocent")
                .lastName("Mangut")
                .departmentId(1L)
                .phone("090987654323")
                .build();
        when(service.save(employee)).thenReturn(Mono.just(employee));

        String response = webTestClient.post()
                .uri("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee))
                .exchange()
                .expectStatus().isBadRequest()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();



    }

    @Test
    public void getAllEmployeesTest(){

        // Insert sample data into the in-memory database
        Employee employee1 = Employee.builder()
                .email("employee1@gmail.com")
                .firstName("Employee")
                .lastName("One")
                .departmentId(1L)
                .phone("1234567890")
                .build();

        Employee employee2 = Employee.builder()
                .email("employee2@gmail.com")
                .firstName("Employee")
                .lastName("Two")
                .departmentId(1L)
                .phone("9876543210")
                .build();

        when(service.getAllEmployees()).thenReturn(Flux.just(employee1, employee2));
        String response = webTestClient.get()
                .uri("/api/v1/employees")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .blockFirst();

        assertTrue(response.contains(employee1.getLastName()));
    }

    @Test
    public void updateEmployeeTest() {
        Employee employee = Employee.builder()
                .id(1L)
                .email("innocent@gmail.com")
                .firstName("Innocent")
                .lastName("Mangut")
                .departmentId(1L)
                .phone("090987654323")
                .build();

        // Mock the editEmployee method to return a Mono with an updated employee
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .email("updated@gmail.com")
                .firstName("Updated")
                .lastName("Employee")
                .departmentId(2L) // Updated department ID
                .phone("9876543210")
                .build();

        when(service.editEmployee(employee.getId(), employee)).thenReturn(Mono.just(updatedEmployee));

        // Send the PUT request to update the employee
        webTestClient.put()
                .uri("/api/v1/employee/{id}", employee.getId()) // Use "employees" instead of "employee"
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedEmployee))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult();


    }

    @Test
    public void deleteEmployeeTest() {
        Long employeeId = 1L; // Replace with an existing employee ID

        when(service.deleteEmployee(employeeId)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/employees/{id}", employeeId)
                .exchange()
                .expectStatus().isNoContent();
    }





}
