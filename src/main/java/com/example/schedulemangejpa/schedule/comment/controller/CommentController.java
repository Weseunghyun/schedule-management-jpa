package com.example.schedulemangejpa.schedule.comment.controller;

import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentRequestDto;
import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.service.CommentService;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestBody CreateCommentRequestDto requestDto
    ){
        CreateCommentResponseDto responseDto = commentService.createComment(
            requestDto.getScheduleId(),
            requestDto.getContent()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
