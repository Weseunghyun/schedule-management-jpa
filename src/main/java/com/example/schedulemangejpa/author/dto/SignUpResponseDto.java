package com.example.schedulemangejpa.author.dto;

import java.time.LocalDateTime;
import lombok.Getter;

//회원가입이 성공적으로 진행되면 반환할 dto (비밀번호를 제외한 데이터를 넘겨줌)
@Getter
public class SignUpResponseDto {
    private final Long authorId;
    private final String authorName;
    private final String authorEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public SignUpResponseDto(Long authorId, String authorName, String authorEmail,
        LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
