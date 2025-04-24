package com.UserService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UserService.entity.User;
import com.UserService.entity.UserRole;
import com.UserService.repository.UserRepo;
import com.UserService.utils.AuthResponse;
import com.UserService.utils.LoginRequest;
import com.UserService.utils.RegisterRequest;

@Service
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
            var user=User.builder()
                        .fullName(request.getFullname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(UserRole.USER)
                        .build();

            User saveduser=userRepo.save(user);
            var accessToken=jwtService.generateToken(saveduser);
            var refreshToken=refTokenService.creatRefreshToken(saveduser.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
    

        public AuthResponse login(LoginRequest login)
        {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

            var loadUser=userRepo.findByEmail(login.getEmail()).orElseThrow(()->new UsernameNotFoundException("USERNAME NOT FOUND..."));
            var accessToken=jwtService.generateToken(loadUser);
            var refreshToken=refTokenService.creatRefreshToken(loadUser.getEmail());
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
}
