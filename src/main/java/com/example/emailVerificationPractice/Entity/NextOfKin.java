package com.example.emailVerificationPractice.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

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
public class NextOfKin {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String nameofNextOfKin;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
