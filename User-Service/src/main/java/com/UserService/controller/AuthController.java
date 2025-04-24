package com.UserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UserService.entity.RefreshToken;
import com.UserService.entity.User;
import com.UserService.service.AuthService;
import com.UserService.service.JWTService;
import com.UserService.service.RefreshTokenService;
import com.UserService.utils.AuthResponse;
import com.UserService.utils.LoginRequest;
import com.UserService.utils.RefreshTokenRequest;
import com.UserService.utils.RegisterRequest;

@RestController
@RequestMapping("/api/v1")
public class AuthController 
{
   private final AuthService authService;
    private final RefreshTokenService refTokenService;
    private final JWTService jwtService;

    @Autowired
    AuthController(AuthService authService,RefreshTokenService refTokenServicce,JWTService jwtService)
    {
        this.authService=authService;
        this.refTokenService=refTokenServicce;
        this.jwtService=jwtService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request)
    {
        AuthResponse response=authService.register(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest)
    {

        AuthResponse response=authService.login(loginRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request)
    {
        RefreshToken refreshToken=refTokenService.verifyRefreshToken(request.getRefreshToken());
        User user=refreshToken.getUser();
        String accessToken=jwtService.generateToken(user);
        AuthResponse response=AuthResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken.getRefreshToken())
                                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
