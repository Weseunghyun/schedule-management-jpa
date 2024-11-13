package com.example.schedulemangejpa.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final String title;
    private final String content;

    public CreateScheduleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
