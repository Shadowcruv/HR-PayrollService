package com.example.emailVerificationPractice.Controller;

import com.example.emailVerificationPractice.Entity.DTO.EmployeeDTO;
import com.example.emailVerificationPractice.Entity.Employee;
import com.example.emailVerificationPractice.Service.EmployeeResponsibleService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for Employee application
 * @Miracle
 */
@RequestMapping("/employee")
@RestController
public class EmployeeController {

    private final EmployeeResponsibleService employeeResponsibleService;

    @Autowired
    public EmployeeController(EmployeeResponsibleService employeeResponsibleService) {
        this.employeeResponsibleService = employeeResponsibleService;
    }
    /**
     * get all employee
     * @return ResponseEntity<List<Employee></>></>
     */
    @GetMapping("/get-all-employee")
    public ResponseEntity<List<EmployeeDTO>>  getAllEmployee(){
        List<EmployeeDTO> employeeList = employeeResponsibleService.getAllEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
    /**
     * get an employee
     * @param id
     * @return ResponseEntity<Employee>
     */
    @GetMapping("/get-employee")
    public ResponseEntity<EmployeeDTO>  getEmployee(@RequestParam Long id){
        EmployeeDTO employee = employeeResponsibleService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeResponsibleService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/update-employee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeResponsibleService.updateEmployee(employee),HttpStatus.OK);
    }

    @DeleteMapping("/delete-employee")
    public ResponseEntity<String> delete(@RequestParam Long id) throws BadRequestException {
        return new ResponseEntity<>(employeeResponsibleService.deleteEmployee(id), HttpStatus.OK);
    }

}
