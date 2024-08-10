package com.example.emailVerificationPractice.Entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
public class Employee {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    public String name;
    @Column(unique = true, nullable = false)
    public String email;
    private String bankAccountNumber;
    private Date employedDate;
    private String bankName;
    private boolean approved = false;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<NextOfKin> nextOfKin;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EducationHistory> educationHistory;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Children> children;

}
