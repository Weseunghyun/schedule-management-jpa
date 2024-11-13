package com.example.schedulemangejpa.author.controller;

import com.example.schedulemangejpa.author.dto.SignUpRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //회원가입 진행 Post 메서드
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(
        @RequestBody SignUpRequestDto requestDto
    ){
        SignUpResponseDto responseDto = authorService.signUp(
            requestDto.getAuthorName(),
            requestDto.getAuthorEmail(),
            requestDto.getPassword()
            );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
