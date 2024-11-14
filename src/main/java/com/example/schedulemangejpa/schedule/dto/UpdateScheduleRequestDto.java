package com.example.schedulemangejpa.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank(message = "제목은 필수값 입니다.")
    @Size(min = 3, max = 20, message = "제목은 3자 이상 20자 이내여야 합니다.")
    private final String title;

    @NotBlank(message = "본문 내용은 필수값 입니다.")
    private final String content;

    @NotBlank(message = "패스워드는 필수값 입니다.")
    private final String password;

    public UpdateScheduleRequestDto(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }
}
