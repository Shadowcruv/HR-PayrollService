package com.example.emailVerificationPractice.Service;


import com.example.emailVerificationPractice.Entity.ApiRole;
import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Entity.DatabaseExcluded.UserPassword;

import com.example.emailVerificationPractice.Repository.ApiRoleRepository;
import com.example.emailVerificationPractice.Repository.ApiUserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApiServiceImpl implements UserDetailsService {


    private final ApiUserRepository apiUserRepository;


    private final ApiRoleRepository apiRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiServiceImpl(ApiUserRepository apiUserRepository,
                           ApiRoleRepository apiRoleRepository, PasswordEncoder passwordEncoder
                         ) {
        this.apiUserRepository = apiUserRepository;

        this.apiRoleRepository = apiRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public String saveApiUser(ApiUser apiUser) {
        return validate(apiUser);
    }

    public String validate(ApiUser apiUser){
        validateEmail(apiUser);
        return checkingUser(apiUser);


    }

    public void  validateEmail(ApiUser apiUser){

    }

    public List<ApiUser> getAdminUsers(){
        List<ApiUser> adminUsers = apiUserRepository.findByroleName("Admin");
        return adminUsers;
    }

    private String checkingUser(ApiUser apiUser) {
        Optional<ApiUser> validation =  apiUserRepository.findOptionUserName(apiUser.getUserName());
        if (validation.isPresent()){
            if(apiUser.getUserName().equalsIgnoreCase(apiUserRepository.findByuserName(apiUser.getUserName()).getUserName())
            ){
                return "email already taken";
            }else {
                saveApiUser1(apiUser);
                return "success";
            }

        }
        saveApiUser1(apiUser);
        return "success";
    }

    public void saveApiUser1(ApiUser apiUser){
        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        apiUser.setAuthoritiess(List.of(new ApiRole("role_Admin")));  //it's meant to be role_Seller but for Test purpose make it role Admin
        apiUserRepository.save(apiUser);
    }


    public ApiUser retrieveUser(String username) {
        ApiUser apiUser = apiUserRepository.findByuserName(username);

        return apiUser;
    }





    public List<ApiUser> retrieveUsers() {
      List<ApiUser> apiUsers = apiUserRepository.findAll();
        return apiUsers;
    }

    //TODO List<ApiUser> retrieveUsersAdminRoleS()
//    public List<ApiUser> retrieveUsersAdminRoleS(){
//        List<ApiUser> apiUsers = apiUserRepository.findUsersByAdmin();
//        return apiUsers;
//    }


    public ApiUser updateUserRoleAdmin( String email){
        ApiUser retrievedStudent =  apiUserRepository.findOptionUserName(email).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedStudent.setAuthoritiess(List.of(new ApiRole("role_Admin")));

        return apiUserRepository.save(retrievedStudent);

    }



    public void deleteUser(String username) {
     //   apiUserRepository.deleteByuserName(username);
        ApiUser apiUser = apiUserRepository.findByuserName(username);
        apiUserRepository.deleteById(apiUser.getId());
      // apiUserRepository.remove
    }


    public void updateProfile(UserDetails userDetails, ApiUser apiUser) {
        ApiUser retrievedUser = apiUserRepository.findOptionUserName(userDetails.getUsername()).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedUser.setFullName(apiUser.getFullName());
        retrievedUser.setMobile(apiUser.getMobile());
        retrievedUser.setAddress(apiUser.getAddress());
        apiUserRepository.save(retrievedUser);
    }


    public void updatePassword(UserDetails userDetails, UserPassword userPassword) {
        ApiUser retrievedUser = apiUserRepository.findOptionUserName(userDetails.getUsername()).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedUser.setPassword(passwordEncoder.encode(userPassword.getText()));

        apiUserRepository.save(retrievedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserRepository.findByuserName(username);
        if (apiUser == null){
            System.out.println("ApiUser not found in the database");
            throw new UsernameNotFoundException("ApiUser not found in the database");
        }else {
            System.out.println("User found in the database: "+ username);
        }
        Collection<SimpleGrantedAuthority> authoritiessList = new ArrayList<>();
        apiUser.getAuthoritiess().forEach(apiRole->{
            authoritiessList.add(new SimpleGrantedAuthority(apiRole.getName()));
        });


       /*
       Collection<ApiRole> authoritiess = apiUser.getAuthoritiess();
        for(ApiRole apiRole: authoritiess){
            authoritiessList.add(new SimpleGrantedAuthority(apiRole.getName()));

        }
      */

        return new org.springframework.security.core.userdetails.User(apiUser.getUserName(),apiUser.getPassword(),apiUser.getEnabled(), true,
                true, true, authoritiessList);
    }



}