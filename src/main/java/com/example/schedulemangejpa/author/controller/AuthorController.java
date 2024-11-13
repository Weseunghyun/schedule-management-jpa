package com.example.schedulemangejpa.author.controller;

import com.example.schedulemangejpa.author.dto.AuthorResponseDto;
import com.example.schedulemangejpa.author.dto.DeleteAuthorRequestDto;
import com.example.schedulemangejpa.author.dto.LoginRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.service.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
// API 경로 설정: /api/authors로 모든 요청이 라우팅됨
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //회원가입 진행 Post 메서드
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(
        @Valid @RequestBody SignUpRequestDto requestDto
    ) {
        // AuthorService의 signUp 메서드를 호출하여 회원가입을 처리하고, 응답 DTO를 생성
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
        @Valid @RequestBody DeleteAuthorRequestDto requestDto
    ) {
        authorService.deleteAuthor(authorId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그인 쿠키/세션을 활용
    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @Valid @RequestBody LoginRequestDto requestDto,
        HttpServletRequest request
    ) {
        //서비스 계층 login 메서드를 통해 로그인이 정상적으로 진행되는지 검증받음
        boolean isValidateLogin = authorService.login(
            requestDto.getAuthorEmail(),
            requestDto.getPassword(),
            request
        );

        //정상적으로 로그인이 됐다면 OK 코드를 보냄
        if (isValidateLogin) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        //정상적으로 로그인 되지않았으면 UNAUTHORIZED 코드를 보냄
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //로그아웃하여 세션을 무효화 시키는 서비스 메서드를 호출
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authorService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
