package com.example.schedulemangejpa.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {

    @NotBlank(message = "패스워드는 필수값 입니다.")
    private final String password;

    public DeleteScheduleRequestDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
