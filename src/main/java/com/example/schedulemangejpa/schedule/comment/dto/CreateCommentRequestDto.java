package com.example.schedulemangejpa.schedule.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private final Long scheduleId;
    private final String content;

    public CreateCommentRequestDto(Long scheduleId, String content) {
        this.scheduleId = scheduleId;
        this.content = content;
    }
}
