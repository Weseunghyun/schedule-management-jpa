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

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(
        @Valid @RequestBody CreateCommentRequestDto requestDto
    ) {
        CreateCommentResponseDto responseDto = commentService.createComment(
            requestDto.getScheduleId(),
            requestDto.getContent()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto requestDto
    ) {
        UpdateCommentResponseDto responseDto = commentService.updateComment(
            commentId,
            requestDto.getContent(),
            requestDto.getPassword()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long commentId,
        @Valid @RequestBody DeleteCommentRequestDto requestDto
    ) {
        commentService.deleteComment(commentId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
