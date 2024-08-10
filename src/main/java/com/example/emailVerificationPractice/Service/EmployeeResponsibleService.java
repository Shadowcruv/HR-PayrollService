package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Config.Settings;
import com.example.emailVerificationPractice.Entity.Children;
import com.example.emailVerificationPractice.Entity.DTO.ChildrenDTO;
import com.example.emailVerificationPractice.Entity.DTO.EducationHistoryDTO;
import com.example.emailVerificationPractice.Entity.DTO.EmployeeDTO;
import com.example.emailVerificationPractice.Entity.DTO.NextOfKinDTO;
import com.example.emailVerificationPractice.Entity.EducationHistory;
import com.example.emailVerificationPractice.Entity.Employee;
import com.example.emailVerificationPractice.Entity.NextOfKin;
import com.example.emailVerificationPractice.Repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeResponsibleService {

    private final EmployeeRepository employeeRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Settings settings;
    private final CacheManager cacheManager;

    @Autowired
    public EmployeeResponsibleService(EmployeeRepository employeeRepository, KafkaTemplate<String, String> kafkaTemplate, Settings settings, CacheManager cacheManager) {
        this.employeeRepository = employeeRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.settings = settings;
        this.cacheManager = cacheManager;
    }


    public List<EmployeeDTO> getAllEmployees(){
       System.out.println("not from cache");
       return getEmployeeIds().stream().map(this::getEmployee).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "employees", key="#id")
    public EmployeeDTO getEmployee(Long id){
        System.out.println("not from cache");
        Employee employee =  employeeRepository.findById(id).orElse(null);
        return convertToDTO(employee);
    }


    public List<Long> getEmployeeIds(){
        Cache cache = cacheManager.getCache("empo");
        if(cache == null){
            throw new IllegalStateException("Cache employeeList does not exist");
        }
        List<Long> idList = cache.get("id", List.class);
        if (idList != null){
            return idList;
        }
        idList = employeeRepository.findAll().stream()
                .map(Employee::getId).collect(Collectors.toList()); //TODO: finish this method when you come back
        cache.put("id",idList);
        return idList;
    }

    @CacheEvict(cacheNames = "emplo", allEntries = true)
    @CachePut(cacheNames = "employees", key="#employee.id")
    public String saveEmployee(Employee employee){

        if(employee.getNextOfKin().size() >= 2 && employee.getEducationHistory().size() >=2 && employee.getChildren().size() >= 2){
            for (Children children : employee.getChildren()) {
                children.setEmployee(employee);
            }
            for (EducationHistory educationHistory : employee.getEducationHistory()) {
                educationHistory.setEmployee(employee);
//                if(!educationHistory.getStrDateCompleted().isEmpty()){
//                    try {
//                        LocalDate graduatedDate = LocalDate.parse(educationHistory.getStrDateCompleted());
//                       educationHistory.setDateCompleted(graduatedDate);
//                    }catch (DateTimeParseException e){
//                        return "error message: Invalid date format. Please provide dates in ISO format (YYYY-MM-DD).";
//                    }
//                }
            }
            for (NextOfKin nextOfKin : employee.getNextOfKin()) {
                nextOfKin.setEmployee(employee);
            }
//            if(!employee.getStrEmploymentDate().isEmpty()){
//                try {
//                    LocalDate employedDate = LocalDate.parse(employee.getStrEmploymentDate());
//                    employee.setEmployedDate(employedDate);
//                }catch (DateTimeParseException e){
//                    return "error message: Invalid date format. Please provide dates in ISO format (YYYY-MM-DD).";
//                }
//
//            }
            Employee employee1 = employeeRepository.save(employee);
            if(settings.sendEmail()){
                kafkaTemplate.send("orderTopic", String.valueOf(employee1.getId()));
            }
            return "saved employee records";

        }else{
            throw new RuntimeException("next of Kin, education history and the children must have a minimum requirement of 2 records");
        }

    }

    @Transactional
    @CacheEvict(cacheNames = "emplo", allEntries = true)
    public Employee updateEmployee(Employee employee){
        Employee savedEmployee;
        Optional<Employee> checkSavedEmployee = employeeRepository.findById(employee.getId());
        if(checkSavedEmployee.isPresent()){
            savedEmployee = checkSavedEmployee.get();
        }else{
            throw new RuntimeException("No such Employee exists");
        }
        copyEmployee(savedEmployee, employee);
        cacheManager.getCache("employee").put(employee.getId(), employee);
        return savedEmployee;
    }

    public void copyEmployee(Employee savedEmployee, Employee currentEmployee){
        savedEmployee.setName(currentEmployee.getName());
    }

    @CacheEvict(cacheNames = {"employees", "employeeList"},allEntries = true)
    public String deleteEmployee(Long id) throws BadRequestException {
            Optional<Employee> optional = employeeRepository.findById(id);
            if(optional.isPresent()){
                employeeRepository.delete(optional.get());
                return "Deleted successfully";
            }else{
                throw new BadRequestException("Employee doesn't exist");
            }
    }

    public EmployeeDTO convertToDTO(Employee employee){
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                                        .id(employee.getId())
                                        .name(employee.getName())
                                        .bankName(employee.getBankName())
                                        .email(employee.getEmail())
                                        .bankAccountNumber(employee.getBankAccountNumber())
                                        .employedDate(employee.getEmployedDate())
                                        .approved(employee.isApproved())
                                        .build();

        employeeDTO.setNextOfKin(convertNextofKinListToDTO(employee.getNextOfKin()));
        employeeDTO.setChildren(convertChildrenListToDTO(employee.getChildren()));
        employeeDTO.setEducationHistory(convertEducationHistoryToDTO(employee.getEducationHistory()));
        return employeeDTO;
    }

    public List<NextOfKinDTO> convertNextofKinListToDTO(List<NextOfKin> nextOfKins){
       return nextOfKins.stream().map( nextOfKin ->
                                NextOfKinDTO.builder()
                                        .nameofNextOfKin(nextOfKin.getNameofNextOfKin())
                                        .id(nextOfKin.getId()).build()
                                ).collect(Collectors.toList());
    }

    public List<ChildrenDTO> convertChildrenListToDTO(List<Children> children){
        return children.stream().map( child ->
                            ChildrenDTO.builder()
                                    .name(child.getName())
                                    .id(child.getId()).build()
                    ).collect(Collectors.toList());
    }

    public List<EducationHistoryDTO> convertEducationHistoryToDTO(List<EducationHistory> educationHistories){
        return educationHistories.stream().map( educationHistory ->
                                    EducationHistoryDTO.builder()
                                            .schoolName(educationHistory.getSchoolName())
                                            .dateCompleted(educationHistory.getDateCompleted())
                                            .id(educationHistory.getId()).build()
                            ).collect(Collectors.toList());
    }



}
