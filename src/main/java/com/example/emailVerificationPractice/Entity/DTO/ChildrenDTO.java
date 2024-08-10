package com.example.emailVerificationPractice.Entity.DTO;

import lombok.*;

import java.io.Serializable;



@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenDTO implements Serializable {
    private Long id;
    private String name;
    private EmployeeDTO employee;

}
