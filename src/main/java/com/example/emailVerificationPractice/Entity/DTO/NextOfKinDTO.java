package com.example.emailVerificationPractice.Entity.DTO;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NextOfKinDTO implements Serializable {
    private Long id;
    private String nameofNextOfKin;
    private EmployeeDTO employee;
}
