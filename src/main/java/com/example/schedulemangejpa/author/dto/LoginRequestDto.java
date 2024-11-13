package com.example.schedulemangejpa.author.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private final String authorEmail;
    private final String password;

    public LoginRequestDto(String authorEmail, String password) {
        this.authorEmail = authorEmail;
        this.password = password;
    }
}
