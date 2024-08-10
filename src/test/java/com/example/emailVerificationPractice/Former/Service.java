package com.example.emailVerificationPractice.Former;

import com.example.emailVerificationPractice.Entity.*;

import com.example.emailVerificationPractice.Repository.*;
//import com.example.emailVerificationPractice.Repository.RoomRepository;
import com.example.emailVerificationPractice.Service.EmployeeResponsibleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class Service {



    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private EducationHistoryRepository educationHistoryRepository;
    @Autowired
    private ChildrenRepository childrenRepository;
    @Autowired
    private NextOfKinRepository nextOfKinRepository;

    @Autowired
    private EmployeeResponsibleService employeeResponsibleService;



    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Test
    public void saveEmployee(){
        EducationHistory educationHistory1 = EducationHistory.builder().schoolName("Unizik").dateCompleted(new Date()).build();
        EducationHistory educationHistory2 = EducationHistory.builder().schoolName("Jetbrains").dateCompleted(new Date()).build();
        List<EducationHistory> educationHistories = new ArrayList<>();
        educationHistories.add(educationHistory1);
        educationHistories.add(educationHistory2);

        Children children1 = Children.builder().name("Odinaka").build();
        Children children2 = Children.builder().name("Chisom").build();
        List<Children> childrenList = new ArrayList<>();
        childrenList.add(children1);
        childrenList.add(children2);

        NextOfKin nextOfKin1 = NextOfKin.builder().nameofNextOfKin("Dede").build();
        NextOfKin nextOfKin2 = NextOfKin.builder().nameofNextOfKin("Dechi").build();
        List<NextOfKin> kins = new ArrayList<>();
        kins.add(nextOfKin1);
        kins.add(nextOfKin2);


        Employee employee = Employee.builder().name("Miracle Ihediwa").bankName("FirstBank").email("ellamiller8901@gmail.com").bankAccountNumber("2351843").educationHistory(educationHistories).children(childrenList).nextOfKin(kins).build();
        employeeResponsibleService.saveEmployee(employee);
    }







}
