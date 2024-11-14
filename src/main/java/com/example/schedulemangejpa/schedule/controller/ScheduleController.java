package com.example.schedulemangejpa.schedule.controller;

import com.example.schedulemangejpa.schedule.comment.dto.CommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.service.CommentService;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.dto.CreateScheduleResponseDto;
import com.example.schedulemangejpa.schedule.dto.DeleteScheduleRequestDto;
import com.example.schedulemangejpa.schedule.dto.SchedulePageResponseDto;
import com.example.schedulemangejpa.schedule.dto.ScheduleReponseDto;
import com.example.schedulemangejpa.schedule.dto.UpdateScheduleRequestDto;
import com.example.schedulemangejpa.schedule.service.ScheduleService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// API 경로 설정: /api/schedules로 모든 요청이 라우팅됨
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;

    // 스케줄 생성 API, 작성자 ID와 요청 본문을 받아 새로운 스케줄 생성
    @PostMapping("/{authorId}")
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
        @PathVariable Long authorId, // 경로에서 스케줄 ID를 받아옴
        @Valid @RequestBody CreateScheduleRequestDto requestDto // 요청 본문에서 제목과 내용을 받아옴
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
    ) {
        ScheduleReponseDto reponseDto = scheduleService.findByScheduleId(scheduleId);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    // 스케줄 수정 API, 스케줄 ID와 요청 본문을 받아 스케줄 정보 수정
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReponseDto> updateSchedule(
        @PathVariable Long scheduleId,
        @Valid @RequestBody UpdateScheduleRequestDto requestDto // 요청 본문에서 제목, 내용, 비밀번호를 받아옴
    ) {
        ScheduleReponseDto reponseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return new ResponseEntity<>(reponseDto, HttpStatus.OK);
    }

    // 스케줄 삭제 API, 스케줄 ID와 요청 본문을 받아 스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable Long scheduleId,
        @Valid @RequestBody DeleteScheduleRequestDto requestDto // 요청 본문에서 비밀번호를 받아옴
    ) {
        scheduleService.deleteSchedule(scheduleId, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //특정 게시글에 달린 댓글들을 조회
    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAllCommentsByScheduleID(
        @PathVariable Long scheduleId
    ) {
        List<CommentResponseDto> responseDtoList = commentService.findAllComments(scheduleId);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //게시글 페이지네이션
    @GetMapping("/paginated")
    public ResponseEntity<SchedulePageResponseDto> getSchedules(
        // 페이지 번호 (기본값: 1). 클라이언트에서 제공하지 않으면 1 페이지로 설정
        @RequestParam(defaultValue = "1") int page,
        // 페이지 크기 (기본값: 10). 한 페이지에 보여줄 항목 수를 설정
        @RequestParam(defaultValue = "10") int size
    ) {
        // 서비스 계층 메서드 호출하여 일정 목록 및 페이지 정보 조회
        // 요청 시 페이지 번호는 1부터 시작하는 반면, Pageable은 0부터 시작하므로 -1 처리
        SchedulePageResponseDto pageResponseDto = scheduleService.getSchedules(page - 1, size);

        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
    }
}
