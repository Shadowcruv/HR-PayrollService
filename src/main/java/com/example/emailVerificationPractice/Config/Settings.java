package com.example.emailVerificationPractice.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Settings {

    @Value(("${email_required}"))
    public boolean requireEmail;
    public boolean sendEmail(){
        return requireEmail;
    }
}
