package com.example.schedulemangejpa.author.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String authorEmail;

    @NotBlank(message = "비밀번호는 필수값 입니다")
    private final String password;

    public LoginRequestDto(String authorEmail, String password) {
        this.authorEmail = authorEmail;
        this.password = password;
    }
}
