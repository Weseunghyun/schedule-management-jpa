package com.example.schedulemangejpa.schedule.controller;

import com.example.schedulemangejpa.schedule.dto.CreateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import com.example.schedulemangejpa.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
}
