package com.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserService.entity.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer>
{
    
}
