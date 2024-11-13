package com.example.schedulemangejpa.schedule.service;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import com.example.schedulemangejpa.schedule.dto.ScheduleReponseDto;
import com.example.schedulemangejpa.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import com.example.schedulemangejpa.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ScheduleReponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Author author = findSchedule.getAuthor();

        if (author.getPassword().equals(requestDto.getPassword())) {
            findSchedule.setTitle(requestDto.getTitle());
            findSchedule.setContent(requestDto.getContent());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }

        scheduleRepository.save(findSchedule);
        return ScheduleReponseDto.toDto(findSchedule);
    }
}
