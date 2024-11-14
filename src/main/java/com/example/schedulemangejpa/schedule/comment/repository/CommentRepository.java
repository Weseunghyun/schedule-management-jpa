package com.example.schedulemangejpa.schedule.comment.repository;

import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
