package com.example.schedulemangejpa.schedule.repository;

import com.example.schedulemangejpa.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

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
