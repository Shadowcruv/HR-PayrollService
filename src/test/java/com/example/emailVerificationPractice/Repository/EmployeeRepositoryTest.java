package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private  EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void beTrueIfEmailExits() {
        //given
        Employee employee = Employee.builder()
                .name("Emma")
                .email("emma@gmail.com").build();

        employeeRepository.save(employee);

        //when
     boolean result = employeeRepository.selectExitsEmail("emma@gmail.com");


        //then
        assertThat(result).isTrue();
    }
    @Test
    void beFalseIfEmailNotExits() {
        //given
         String email = "emma@gmail.com";

        //when
        boolean result = employeeRepository.selectExitsEmail("emma@gmail.com");

        //then
        assertThat(result).isFalse();
    }
}