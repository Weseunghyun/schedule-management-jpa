package com.example.schedulemangejpa.schedule.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteCommentRequestDto {

    @NotBlank(message = "비밀번호는 필수값 입니다")
    private final String password;

    //dto 안에 하나만 넣고 던지면 에러가난다.
    public DeleteCommentRequestDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
