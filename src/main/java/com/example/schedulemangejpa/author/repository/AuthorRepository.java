package com.example.schedulemangejpa.author.repository;

import com.example.schedulemangejpa.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
