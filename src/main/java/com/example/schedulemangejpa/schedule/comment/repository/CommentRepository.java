package com.example.schedulemangejpa.schedule.comment.repository;

import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySchedule(Schedule schedule);

    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Does not exist id = " + id)
        );
    }
}
