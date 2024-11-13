package com.example.schedulemangejpa.author.dto;

import java.time.LocalDateTime;
import lombok.Getter;

//SignUpResponseDto와 똑같지만 가독성과 혹여나 나중에 조회에서 원하는 요구사항이 달라질 경우를
//대비해 따로 분리하여 Dto 클래스를 생성.
@Getter
public class AuthorResponseDto {

    private final Long id;
    private final String authorName;
    private final String authorEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public AuthorResponseDto(Long id, String authorName, String authorEmail,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {
        this.id = id;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
