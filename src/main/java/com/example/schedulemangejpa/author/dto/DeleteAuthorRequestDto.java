package com.example.schedulemangejpa.author.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeleteAuthorRequestDto {

    private final String password;

    //dto 안에 하나만 넣고 던지면 에러가난다.
    public DeleteAuthorRequestDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
