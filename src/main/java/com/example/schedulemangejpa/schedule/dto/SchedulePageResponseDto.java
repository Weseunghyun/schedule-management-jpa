package com.example.schedulemangejpa.schedule.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 일정 데이터와 페이지 정보를 포함하는 응답 DTO
@Getter
@AllArgsConstructor
public class SchedulePageResponseDto {
    private List<ScheduleWithCommentCountDto> schedules; // 일정 목록을 담는 리스트
    private PageInfo pageInfo; // 페이징 정보 (페이지 번호, 크기 등)

    // PageInfo 내장 클래스: 페이지네이션 정보를 담음
    @Getter
    @AllArgsConstructor
    public static class PageInfo {
        private int page; // 현재 페이지 번호
        private int size;  // 페이지 크기
        private int totalElements; // 전체 항목 수
        private int totalPages;  // 전체 페이지 수
    }
}
