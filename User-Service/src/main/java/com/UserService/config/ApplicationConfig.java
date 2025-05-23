package com.UserService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.UserService.repository.UserRepo;

@Configurable
public class ApplicationConfig 
{

    @Autowired
    private UserRepo userRepo;

    public ApplicationConfig(UserRepo userRepo)
    {
        this.userRepo=userRepo;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return email->this.userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }


    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authPOV= new DaoAuthenticationProvider();
        authPOV.setUserDetailsService(userDetailsService());
        authPOV.setPasswordEncoder(passwordEncoder());
        return authPOV;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    } 


}
