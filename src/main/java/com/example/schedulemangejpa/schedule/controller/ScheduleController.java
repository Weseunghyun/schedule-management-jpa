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
// API 경로 설정: /api/schedules로 모든 요청이 라우팅됨
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 스케줄 생성 API, 작성자 ID와 요청 본문을 받아 새로운 스케줄 생성
    @PostMapping("/{authorId}")
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
        @PathVariable Long authorId, // 경로에서 스케줄 ID를 받아옴
        @RequestBody CreateScheduleRequestDto requestDto // 요청 본문에서 제목과 내용을 받아옴
    ) {
        // 서비스에서 스케줄 생성 로직을 호출하고, 응답 DTO를 반환
        CreateScheduleResponseDto responseDto = scheduleService.createSchedule(
            authorId,
            requestDto.getTitle(),
            requestDto.getContent()
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 모든 스케줄 조회 API
    @GetMapping
    public ResponseEntity<List<ScheduleReponseDto>> findAllSchedules() {
        List<ScheduleReponseDto> reponseDtos = scheduleService.findAllSchedules();
        return new ResponseEntity<>(reponseDtos, HttpStatus.OK);
    }

    // 특정 스케줄 ID로 조회하는 API
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReponseDto> findByScheduleId(
        @PathVariable Long scheduleId // 경로에서 스케줄 ID를 받아옴
    ){
        ScheduleReponseDto reponseDto = scheduleService.findByScheduleId(scheduleId);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    // 스케줄 수정 API, 스케줄 ID와 요청 본문을 받아 스케줄 정보 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReponseDto> updateSchedule(
        @PathVariable Long scheduleId,
        @RequestBody UpdateScheduleRequestDto requestDto // 요청 본문에서 제목, 내용, 비밀번호를 받아옴
    ){
        ScheduleReponseDto reponseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    // 스케줄 삭제 API, 스케줄 ID와 요청 본문을 받아 스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable Long scheduleId,
        @RequestBody DeleteScheduleRequestDto requestDto // 요청 본문에서 비밀번호를 받아옴
    ){
        scheduleService.deleteSchedule(scheduleId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
