package com.example.schedulemangejpa.schedule.dto;

import java.time.LocalDateTime;
import lombok.Getter;

//SchedulePageResponseDto 에 담길 페이지들의 정보를 담는 Dto
@Getter
public class ScheduleWithCommentCountDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String authorName;  // 작성자 이름
    private final long commentCount;  // 댓글 개수
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleWithCommentCountDto(Long id, String title, String content, String authorName,
        long commentCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
