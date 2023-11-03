package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EmployeeWebfluxApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeWebfluxApiApplication.class, args);
	}

}
