package com.UserService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserService.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer>
{
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    
}
