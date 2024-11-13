package com.example.schedulemangejpa.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {

    private final String password;

    public DeleteScheduleRequestDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
