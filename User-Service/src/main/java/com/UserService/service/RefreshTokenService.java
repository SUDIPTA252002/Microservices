package com.UserService.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.UserService.entity.RefreshToken;
import com.UserService.entity.User;
import com.UserService.repository.RefreshTokenRepo;
import com.UserService.repository.UserRepo;

@Service
public class RefreshTokenService 
{

    private UserRepo userRepo;
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    public RefreshTokenService(UserRepo userRepo,RefreshTokenRepo refreshTokenRepo)
    {
        this.userRepo=userRepo;
        this.refreshTokenRepo=refreshTokenRepo;
    }
    

    public RefreshToken creatRefreshToken(String email)
    {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("USERNAME DOES NOT EXIST..."));
        RefreshToken refreshToken=user.getRefreshToken();

        if(refreshToken==null)
        {
            long refreshTokenValidity=5*60*60*1000;
            refreshToken=RefreshToken.builder()
                        .refreshToken(UUID.randomUUID().toString())
                        .exprirationTime(Instant.now().plusMillis(refreshTokenValidity))
                        .user(user)
                        .build();

            refreshTokenRepo.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken)
    {
        RefreshToken refToken=refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("RefrshToken Not Found"));

        if(refToken.getExprirationTime().compareTo(Instant.now())<0)
        {
            refreshTokenRepo.delete(refToken);
            throw new RuntimeException("REFRESH TOKEN EXPIRED....");
        }
        return refToken;
    }
}
