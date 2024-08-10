package com.example.emailVerificationPractice.Entity.DTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationHistoryDTO implements Serializable {
    private Long id;
    private String schoolName;
    private Date dateCompleted;
    private EmployeeDTO employee;


}
