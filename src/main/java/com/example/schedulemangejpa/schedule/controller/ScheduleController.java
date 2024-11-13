package com.example.schedulemangejpa.schedule.controller;

import com.example.schedulemangejpa.schedule.dto.CreateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import com.example.schedulemangejpa.schedule.dto.DeleteScheduleRequestDto;
import com.example.schedulemangejpa.schedule.dto.ScheduleReponseDto;
import com.example.schedulemangejpa.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/{authorId}")
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
        @PathVariable Long authorId,
        @RequestBody CreateScheduleRequestDto requestDto
    ) {
        CreateScheduleResponseDto responseDto = scheduleService.createSchedule(
            authorId,
            requestDto.getTitle(),
            requestDto.getContent()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleReponseDto>> findAllSchedules() {
        List<ScheduleReponseDto> reponseDtos = scheduleService.findAllSchedules();
        return new ResponseEntity<>(reponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReponseDto> findByScheduleId(
        @PathVariable Long scheduleId
    ){
        ScheduleReponseDto reponseDto = scheduleService.findByScheduleId(scheduleId);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReponseDto> updateSchedule(
        @PathVariable Long scheduleId,
        @RequestBody UpdateScheduleRequestDto requestDto
    ){
        ScheduleReponseDto reponseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable Long scheduleId,
        @RequestBody DeleteScheduleRequestDto requestDto
    ){
        scheduleService.deleteSchedule(scheduleId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
