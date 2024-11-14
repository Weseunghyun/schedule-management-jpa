package com.example.schedulemangejpa.schedule.comment.repository;

import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 스케줄에 속한 댓글 리스트 조회 메서드
    List<Comment> findBySchedule(Schedule schedule);

    // 주어진 ID로 댓글 조회, 없을 경우 예외 발생
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Does not exist id = " + id)
        );
    }
}
