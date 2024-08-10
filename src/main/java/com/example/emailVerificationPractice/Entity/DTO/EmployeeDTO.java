package com.example.emailVerificationPractice.Entity.DTO;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {


    private Long id;
    public String name;
    public String email;
    private String bankAccountNumber;
    private Date employedDate;
    private String bankName;
    private boolean approved = false;
    private List<NextOfKinDTO> nextOfKin;
    private List<EducationHistoryDTO> educationHistory;
    private List<ChildrenDTO> children;

}
