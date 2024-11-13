package com.example.schedulemangejpa.author.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

//작성자 회원가입 진행할 때 요청받는 dto 생성 (이름, 이메일, 패스워드를 받아옴)
@Getter
public class SignUpRequestDto {

    @NotBlank(message = "작성자명은 필수값 입니다.")
    @Size(min = 1, max = 5, message = "작성자명은 1~5자 이내여야 합니다.")
    private final String authorName;

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String authorEmail;

    @NotBlank(message = "비밀번호는 필수값 입니다.")
    private final String password;

    public SignUpRequestDto(String authorName, String email, String password) {
        this.authorName = authorName;
        this.authorEmail = email;
        this.password = password;
    }
}
