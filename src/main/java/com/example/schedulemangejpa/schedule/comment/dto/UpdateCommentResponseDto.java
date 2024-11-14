package com.example.schedulemangejpa.schedule.comment.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateCommentResponseDto {

    private final Long commentId;
    private final Long scheduleId;
    private final Long authorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateCommentResponseDto(Long commentId, Long scheduleId, Long authorId, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.scheduleId = scheduleId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
