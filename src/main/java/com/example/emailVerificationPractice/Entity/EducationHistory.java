package com.example.emailVerificationPractice.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.LazyToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class EducationHistory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String schoolName;
    private Date dateCompleted;


    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
