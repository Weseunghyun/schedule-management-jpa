package com.example.schedulemangejpa.author.service;

import com.example.schedulemangejpa.author.dto.AuthorResponseDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public SignUpResponseDto signUp(String authorName, String authorEmail, String password) {

        Author savedAuthor = authorRepository.save(new Author(authorName, authorEmail, password));

        return new SignUpResponseDto(
            savedAuthor.getId(),
            savedAuthor.getName(),
            savedAuthor.getEmail(),
            savedAuthor.getCreatedAt(),
            savedAuthor.getModifiedAt()
        );
    }

    public AuthorResponseDto findById(Long authorId) {
        Author findAuthor = authorRepository.findByIdOrElseThrow(authorId);
        return new AuthorResponseDto(
            findAuthor.getId(),
            findAuthor.getName(),
            findAuthor.getEmail(),
            findAuthor.getCreatedAt(),
            findAuthor.getModifiedAt()
        );
    }

    public void deleteAuthor(Long authorId, String password) {
        Author findAuthor = authorRepository.findByIdOrElseThrow(authorId);

        //id를 통해 찾은 작성자의 패스워드와 요청할때 받은 패스워드가 일치하다면 삭제 진행
        if (findAuthor.getPassword().equals(password)) {
            authorRepository.delete(findAuthor);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }

    }
}
