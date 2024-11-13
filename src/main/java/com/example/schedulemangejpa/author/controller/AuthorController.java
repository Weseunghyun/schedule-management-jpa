package com.example.schedulemangejpa.author.controller;

import com.example.schedulemangejpa.author.dto.AuthorResponseDto;
import com.example.schedulemangejpa.author.dto.DeleteAuthorRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //회원가입 진행 Post 메서드
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(
        @RequestBody SignUpRequestDto requestDto
    ) {
        SignUpResponseDto responseDto = authorService.signUp(
            requestDto.getAuthorName(),
            requestDto.getAuthorEmail(),
            requestDto.getPassword()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //PathVariable을 통해 받아온 authorId로 해당 작성자의 정보를 조회하여 넘겨주는 Get 메서드
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDto> findAuthorById(@PathVariable Long authorId) {
        AuthorResponseDto responseDto = authorService.findById(authorId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //PathVariable을 통해 authorId를 받고 해당 작성자를 조회 후 Body로 받아온 비밀번호와 동일하다면
    //삭제하고 동일하지 않다면 에러를 반환
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthorById(
        @PathVariable Long authorId,
        @RequestBody DeleteAuthorRequestDto requestDto
    ) {
        authorService.deleteAuthor(authorId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
