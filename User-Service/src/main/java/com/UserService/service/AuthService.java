package com.UserService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.UserService.repository.UserRepo;
import com.UserService.utils.AuthResponse;

public class AuthService 
{
    private UserRepo userRepo;
    private RefreshTokenService refTokenService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authManager;
    private JWTService jwtService;

     @Autowired
    public AuthService(
        PasswordEncoder passwordEncoder, 
        UserRepo userRepo, 
        JWTService jwtService, 
        RefreshTokenService refTokenService, 
        AuthenticationManager authManager) 
        {
            this.passwordEncoder = passwordEncoder;
            this.userRepo = userRepo;
            this.jwtService = jwtService;
            this.refTokenService = refTokenService;
            this.authManager = authManager;
        }

        public AuthResponse register(RegisterRequest request)
        {

        }
    
}
