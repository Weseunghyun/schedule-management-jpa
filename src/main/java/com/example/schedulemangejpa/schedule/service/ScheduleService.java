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

    // 스케줄 생성 로직
    public CreateScheduleResponseDto createSchedule(Long authorId, String title, String content) {
        // 작성자 정보 조회, 존재하지 않으면 예외 발생
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        // 새로운 스케줄 생성 후 작성자 설정 setter가 최선인가?
        Schedule schedule = new Schedule(title, content);
        schedule.setAuthor(author);

        // 스케줄 저장 및 저장된 엔티티 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 응답 DTO 생성 후 반환
        return new CreateScheduleResponseDto(
            savedSchedule.getId(),
            author.getId(),
            savedSchedule.getTitle(),
            savedSchedule.getContent(),
            savedSchedule.getCreatedAt(),
            savedSchedule.getModifiedAt()
        );
    }

    // 모든 스케줄 조회 로직
    public List<ScheduleReponseDto> findAllSchedules() {
        return scheduleRepository
            .findAll()
            .stream()
            .map(ScheduleReponseDto::toDto) // 각 스케줄 엔티티를 응답 DTO로 변환
            .toList();
    }

    // 스케줄 ID로 특정 스케줄 조회
    public ScheduleReponseDto findByScheduleId(Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        // 조회된 스케줄을 응답 DTO로 변환
        return ScheduleReponseDto.toDto(findSchedule);
    }

    // 스케줄 업데이트 로직
    public ScheduleReponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto requestDto) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        // 비밀번호 검증 후 제목 및 내용 업데이트
        if (isValidatePassword(findSchedule, requestDto.getPassword())) {
            findSchedule.setTitle(requestDto.getTitle());
            findSchedule.setContent(requestDto.getContent());
        }

        // 변경된 스케줄 저장 후 반환
        scheduleRepository.save(findSchedule);
        return ScheduleReponseDto.toDto(findSchedule);
    }

    // 스케줄 삭제 로직
    public void deleteSchedule(Long scheduleId, String password) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        // 비밀번호 검증 후 스케줄 삭제
        if (isValidatePassword(findSchedule, password)) {
            scheduleRepository.delete(findSchedule);
        }
    }

    // 비밀번호 검증 메서드
    private boolean isValidatePassword(Schedule schedule, String password) {
        //스케줄과 연관관계인 Author객체를 얻어와서 패스워드를 비교
        if (schedule.getAuthor().getPassword().equals(password)) {
            return true;
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }
    }
}
