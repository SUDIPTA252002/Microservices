package com.UserService.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest
{

    private String fullname;
    private String email;
    private String password;

}