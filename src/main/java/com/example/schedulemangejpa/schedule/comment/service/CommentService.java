package com.example.schedulemangejpa.schedule.comment.service;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import com.example.schedulemangejpa.common.config.PasswordEncoder;
import com.example.schedulemangejpa.schedule.comment.dto.CommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.dto.UpdateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import com.example.schedulemangejpa.schedule.comment.repository.CommentRepository;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import com.example.schedulemangejpa.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    // 댓글 생성 서비스 메서드
    public CreateCommentResponseDto createComment(Long scheduleId, Long authorId, String content) {
        // 스케줄과 작성자 정보 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        // 댓글 객체 생성 및 스케줄, 작성자 정보 setter로 설정
        Comment comment = new Comment(content);
        comment.setSchedule(schedule);
        comment.setAuthor(author);

        // 댓글 저장 후 응답 DTO 반환
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponseDto(
            savedComment.getId(),
            savedComment.getSchedule().getId(),
            savedComment.getAuthor().getId(),
            savedComment.getContent(),
            savedComment.getCreatedAt(),
            savedComment.getModifiedAt()
        );
    }

    // 특정 스케줄에 속한 모든 댓글을 조회
    public List<CommentResponseDto> findAllComments(Long scheduleId) {
        // 스케줄 조회 후 해당 스케줄의 댓글 목록 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        List<Comment> commentList = commentRepository.findBySchedule(schedule);

        // CommentResponseDto로 변환하여 반환
        return commentList.stream()
            .map(CommentResponseDto::toDto)
            .toList();
    }

    // 댓글 수정 서비스 메서드
    public UpdateCommentResponseDto updateComment(Long commentId, String content, String password) {
        // 댓글 ID로 댓글 조회
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        String encodedPassword = comment.getAuthor().getPassword();

        // 비밀번호 검증 후 댓글 내용 업데이트
        if (isValidatePassword(password, encodedPassword)) {
            comment.setContent(content);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }

        // 수정된 댓글 저장 및 응답 DTO 반환
        Comment updateComment = commentRepository.save(comment);

        return new UpdateCommentResponseDto(
            updateComment.getId(),
            updateComment.getSchedule().getId(),
            updateComment.getAuthor().getId(),
            updateComment.getContent(),
            updateComment.getCreatedAt(),
            updateComment.getModifiedAt()
        );
    }

    // 댓글 삭제 서비스 메서드
    public void deleteComment(Long commentId, String password) {
        // 댓글 ID로 댓글 조회
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        String encodedPassword = comment.getAuthor().getPassword();

        // 비밀번호 검증 후 삭제 수행
        if (isValidatePassword(password, encodedPassword)) {
            commentRepository.delete(comment);
        }
    }

    // 비밀번호 검증 메서드
    private boolean isValidatePassword(String password, String encodedPassword) {
        //passwordEncoder 의 matches 메서드를 사용
        if (passwordEncoder.matches(password, encodedPassword)) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }
    }
}
