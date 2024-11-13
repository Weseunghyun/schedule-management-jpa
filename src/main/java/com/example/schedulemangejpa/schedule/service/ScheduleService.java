package com.example.schedulemangejpa.schedule.service;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import com.example.schedulemangejpa.schedule.dto.ScheduleReponseDto;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import com.example.schedulemangejpa.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;


    public CreateScheduleResponseDto createSchedule(Long authorId, String title, String content) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Schedule schedule = new Schedule(title, content);

        schedule.setAuthor(author);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(
            savedSchedule.getId(),
            author.getId(),
            savedSchedule.getTitle(),
            savedSchedule.getContent(),
            savedSchedule.getCreatedAt(),
            savedSchedule.getModifiedAt()
        );
    }

    public List<ScheduleReponseDto> findAllSchedules() {
        return scheduleRepository
            .findAll()
            .stream()
            .map(ScheduleReponseDto::toDto)
            .toList();
    }

    public ScheduleReponseDto findByScheduleId(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return ScheduleReponseDto.toDto(findSchedule);
    }
}
