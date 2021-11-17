package com.gachon_food.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginFormDto{
    private String email;
    private String password;
    @Builder
    public LoginFormDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}
