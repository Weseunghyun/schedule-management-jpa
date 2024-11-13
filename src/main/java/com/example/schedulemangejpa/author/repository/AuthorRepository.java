package com.example.schedulemangejpa.author.repository;

import com.example.schedulemangejpa.author.entity.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByEmail(String email);

    default Author findByIdOrElseThrow(long id) {
        return findById(id).
            orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Does not exist id = " + id)
            );
    }

}
