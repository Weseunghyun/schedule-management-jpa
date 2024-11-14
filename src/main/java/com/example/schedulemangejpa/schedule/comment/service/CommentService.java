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
import jakarta.validation.constraints.NotBlank;
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

    public CreateCommentResponseDto createComment(Long scheduleId, Long authorId, String content) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Comment comment = new Comment(content);
        comment.setSchedule(schedule);
        comment.setAuthor(author);

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


    public List<CommentResponseDto> findAllComments(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        List<Comment> commentList = commentRepository.findBySchedule(schedule);

        return commentList.stream()
            .map(CommentResponseDto::toDto)
            .toList();
    }

    public UpdateCommentResponseDto updateComment(Long commentId, String content, String password) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        String encodedPassword = comment.getAuthor().getPassword();

        if (isValidatePassword(password, encodedPassword)) {
            comment.setContent(content);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }

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

    public void deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        String encodedPassword = comment.getAuthor().getPassword();

        if (isValidatePassword(password, encodedPassword)) {
            commentRepository.delete(comment);
        }
    }

    private boolean isValidatePassword(String password, String encodedPassword) {
        if (passwordEncoder.matches(password, encodedPassword)) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }
    }
}
