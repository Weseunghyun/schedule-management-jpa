package com.example.schedulemangejpa.schedule.dto;

import com.example.schedulemangejpa.schedule.entity.Schedule;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleReponseDto {

    private final Long id;
    private final Long authorId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ScheduleReponseDto(Long id, Long authorId, String title, String content, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 스케줄 엔티티를 DTO로 변환하는 정적 메서드
    public static ScheduleReponseDto toDto(Schedule schedule) {
        return new ScheduleReponseDto(
            schedule.getId(),
            schedule.getAuthor().getId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getModifiedAt());
    }
}
