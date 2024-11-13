package com.example.schedulemangejpa.author.service;

import com.example.schedulemangejpa.author.dto.AuthorResponseDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import java.util.Optional;
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
}
