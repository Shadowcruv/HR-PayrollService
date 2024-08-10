package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM" +
            " Employee e Where e.email = ?1")
    Boolean selectExitsEmail(String email);

}
