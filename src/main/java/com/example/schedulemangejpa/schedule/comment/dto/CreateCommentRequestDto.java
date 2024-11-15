package com.example.schedulemangejpa.schedule.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotNull
    private final Long scheduleId;

    @NotNull
    private final Long authorId;

    // 댓글 내용에 대한 유효성 검사 설정
    @NotBlank(message = "댓글 내용은 필수값 입니다.")
    @Size(min = 5, max = 200, message = "댓글은 5자 이상 200자 이하여야 합니다.")
    private final String content;

    public CreateCommentRequestDto(Long scheduleId, Long authorId, String content) {
        this.scheduleId = scheduleId;
        this.authorId = authorId;
        this.content = content;
    }
}
