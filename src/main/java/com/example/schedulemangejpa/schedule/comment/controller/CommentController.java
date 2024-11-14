package com.example.schedulemangejpa.schedule.comment.controller;

import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentRequestDto;
import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.dto.DeleteCommentRequestDto;
import com.example.schedulemangejpa.schedule.comment.dto.UpdateCommentRequestDto;
import com.example.schedulemangejpa.schedule.comment.dto.UpdateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 API 엔드포인트
    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(
        @Valid @RequestBody CreateCommentRequestDto requestDto
    ) {
        // 댓글 생성 로직 호출
        CreateCommentResponseDto responseDto = commentService.createComment(
            requestDto.getScheduleId(),
            requestDto.getAuthorId(),
            requestDto.getContent()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 수정 API 엔드포인트
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto requestDto
    ) {
        // 댓글 수정 로직 호출
        UpdateCommentResponseDto responseDto = commentService.updateComment(
            commentId,
            requestDto.getContent(),
            requestDto.getPassword()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제 API 엔드포인트
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long commentId,
        @Valid @RequestBody DeleteCommentRequestDto requestDto
    ) {
        // 댓글 삭제 로직 호출
        commentService.deleteComment(commentId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
