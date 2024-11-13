package com.example.schedulemangejpa.schedule.repository;

import com.example.schedulemangejpa.schedule.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByAuthorId(Long id);
}
