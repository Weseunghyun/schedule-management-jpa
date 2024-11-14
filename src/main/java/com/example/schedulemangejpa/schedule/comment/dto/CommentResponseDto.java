package com.example.schedulemangejpa.schedule.comment.dto;

import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long commentId;
    private final Long scheduleId;
    private final Long authorId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long commentId, Long scheduleId, Long authorId, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.scheduleId = scheduleId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Comment 엔티티를 DTO로 변환하는 메서드
    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
            comment.getId(),
            comment.getSchedule().getId(),
            comment.getAuthor().getId(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getModifiedAt());
    }
}
