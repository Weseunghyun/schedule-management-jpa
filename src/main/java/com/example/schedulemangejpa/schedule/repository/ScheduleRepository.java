package com.example.schedulemangejpa.schedule.repository;

import com.example.schedulemangejpa.schedule.dto.ScheduleWithCommentCountDto;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 일정 목록과 댓글 수를 포함하는 페이징 조회 쿼리
    //@Query 어노테이션을 사용하여 바로 Dto로 변환하도록 함.
    @Query("SELECT new com.example.schedulemangejpa.schedule.dto.ScheduleWithCommentCountDto(" +
        "s.id, s.title, s.content, s.author.name, " +
        "(SELECT COUNT(c) FROM Comment c WHERE c.schedule.id = s.id), " +
        "s.createdAt, s.modifiedAt) " +
        "FROM Schedule s ")
    Page<ScheduleWithCommentCountDto> findAllWithCommentCount(Pageable pageable);

    // 스케줄 ID로 조회 후, 없으면 예외 발생시킨다 Not Found
    default Schedule findByIdOrElseThrow(long id) {
        return findById(id).
            orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Does not exist id = " + id)
            );
    }
}
