package com.example.emailVerificationPractice.Security;

import com.example.emailVerificationPractice.Service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityPasswordConfig{

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ApiServiceImpl apiService;


    @Autowired
    public SecurityPasswordConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ApiServiceImpl apiService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.apiService = apiService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationProvider());
        customAuthenticationFilter.setFilterProcessesUrl("/holy-family/login");

        http
                .cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(request->request.requestMatchers("/admin-page/save").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement(session-> session.sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.getOrBuild();
    }


    @Bean
    public AuthenticationManager authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(apiService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }



}
