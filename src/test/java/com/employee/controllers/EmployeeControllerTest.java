package com.employee.controllers;

import com.employee.models.Employee;
import com.employee.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService service;
    @Test
    public void saveEmployeeTest() throws Exception {
        Employee employee = Employee.builder()
                .email("innocent@gmail.com")
                .firstName("Innocent")
                .lastName("Mangut")
                .departmentId(1L)
                .phone("090987654323")
                .build();

        when(service.save(employee)).thenReturn(Mono.just(employee));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType("application/json")
                        .content("{\"email\":\"valid@gmail.com\",\"firstName\":\"Valid\",\"lastName\":\"Employee\",\"departmentId\":1,\"phone\":\"1234567890\"}"))
                .andExpect(status().isCreated());


    }

}
