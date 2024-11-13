package com.example.schedulemangejpa.author.dto;

import lombok.Getter;

//작성자 회원가입 진행할 때 요청받는 dto 생성 (이름, 이메일, 패스워드를 받아옴)
@Getter
public class SignUpRequestDto {

    private final String authorName;

    private final String authorEmail;

    private final String password;

    public SignUpRequestDto(String authorName, String email, String password) {
        this.authorName = authorName;
        this.authorEmail = email;
        this.password = password;
    }
}
