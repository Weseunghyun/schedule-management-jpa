package com.example.schedulemangejpa.schedule.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Long authorId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CreateScheduleResponseDto(Long id,  Long authorId, String title, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
