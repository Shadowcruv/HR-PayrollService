package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Config.Settings;
import com.example.emailVerificationPractice.Entity.Children;
import com.example.emailVerificationPractice.Entity.EducationHistory;
import com.example.emailVerificationPractice.Entity.Employee;
import com.example.emailVerificationPractice.Entity.NextOfKin;
import com.example.emailVerificationPractice.Repository.EmployeeRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeResponsibleServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    private Settings settings;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    private AutoCloseable autoCloseable;
    private EmployeeResponsibleService employeeResponsibleService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        settings = new Settings();
        employeeResponsibleService = new EmployeeResponsibleService(employeeRepository,kafkaTemplate,settings,cacheManager);
        when(cacheManager.getCache("empo")).thenReturn(cache);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getEmployeeIds() {
        //when
        employeeResponsibleService.getEmployeeIds();
        //then
        verify(employeeRepository).findAll();
    }

    @Test
    void saveEmployee(){
        //given
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

        //when

        employeeResponsibleService.saveEmployee(employee);

        //then
        ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(argumentCaptor.capture());
        Employee value = argumentCaptor.getValue();
        assertThat(value).isEqualTo(employee);

    }

    @Test
    void checkExceptionInSaveEmployee(){
        //given
        EducationHistory educationHistory1 = EducationHistory.builder().schoolName("Unizik").dateCompleted(new Date()).build();
        List<EducationHistory> educationHistories = new ArrayList<>();
        educationHistories.add(educationHistory1);

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

        //when
        //then
       assertThatThrownBy(()->employeeResponsibleService.saveEmployee(employee))
               .isInstanceOf(RuntimeException.class)
               .hasMessageContaining("next of Kin, education history and the children must have a minimum requirement of 2 records");

       verify(employeeRepository, never()).save(any()); // means that employeeRepository was never called to save anything

    }

    @Test
    void deleteEmployee() throws BadRequestException {
        //given
        Long id = 1L;
        //when
        employeeResponsibleService.deleteEmployee(id);
        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(employeeRepository).deleteById(argumentCaptor.capture());
        assertThat(id).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void deleteEmployeeThrowException() throws BadRequestException {
        //given

        Long id = 1L;
        //when
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(()-> employeeResponsibleService.deleteEmployee(id))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Employee doesn't exist");
        verify(employeeRepository, never()).deleteById(any());
        verify(employeeRepository).findById(any());
    }

    @Test
    @Disabled
    void getEmployer() {
        //when

        //then

    }
}

/*
    You can also use the @EXtendWith(MockitoExtension.class) and then delete the following below
    in your code because the annotation does the same:

    private AutoCloseable autoCloseable;
    autoCloseable = MockitoAnnotations.openMocks(this);
    autoCloseable.close();
 */