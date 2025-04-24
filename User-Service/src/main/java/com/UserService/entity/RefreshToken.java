package com.UserService.entity;

import java.time.Instant;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Refresh-Tokens")
public class RefreshToken 
{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false,length=500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant exprirationTime;

    @OneToOne
    private User user;
    
}
