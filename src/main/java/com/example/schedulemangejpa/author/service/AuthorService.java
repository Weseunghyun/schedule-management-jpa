package com.example.schedulemangejpa.author.service;

import com.example.schedulemangejpa.author.dto.SignUpRequestDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;

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
}
