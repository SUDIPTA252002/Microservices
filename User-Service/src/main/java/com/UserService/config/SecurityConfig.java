package com.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.UserService.service.AuthFilterService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig 
{
    private final AuthenticationProvider authProvider;
    private final AuthFilterService authFilterService;

    @Bean
    public SecurityFilterChain secFilterChain(HttpSecurity http)throws SecurityException,Exception
    {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth->auth
            .requestMatchers("api/v1/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);

            return http.build();
            
    }

    
}
