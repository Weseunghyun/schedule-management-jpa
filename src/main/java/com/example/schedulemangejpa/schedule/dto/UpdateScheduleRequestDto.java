package com.example.schedulemangejpa.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final String title;
    private final String content;
    private final String password;

    public UpdateScheduleRequestDto(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }
}
