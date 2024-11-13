package com.example.schedulemangejpa.author.repository;

import com.example.schedulemangejpa.author.entity.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // 이메일을 통해 Author 객체를 조회
    Optional<Author> findAuthorByEmail(String email);

    // ID로 Author 조회 시 존재하지 않으면 NOT_FOUND 예외 발생
    default Author findByIdOrElseThrow(long id) {
        return findById(id).
            orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Does not exist id = " + id)
            );
    }

}
