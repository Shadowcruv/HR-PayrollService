package com.example.emailVerificationPractice.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUser {
    @Id
    @SequenceGenerator(
            name = "api_user_sequence",
            sequenceName = "api_user_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator =  "api_user_sequence"
    )

    private Long Id;
    private String userName;
    private String password;


    //TODO: Update1 : add more properties of the ApiUser

    private String urlPic;
    private String roleName;
    private String fullName;
    private String address;
    private String mobile;
    private Boolean enabled = true;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinTable(
            name = "api_user_authorities",
            joinColumns = @JoinColumn(name = "api_user_id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "api_role_id", referencedColumnName = "Id"))
    private Collection<ApiRole> authoritiess;





}
