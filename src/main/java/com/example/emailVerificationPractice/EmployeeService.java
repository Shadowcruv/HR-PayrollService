package com.example.emailVerificationPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeService {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeService.class, args);
	}

}
