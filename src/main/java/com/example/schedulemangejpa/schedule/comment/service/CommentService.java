package com.example.schedulemangejpa.schedule.comment.service;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import com.example.schedulemangejpa.schedule.comment.dto.CreateCommentResponseDto;
import com.example.schedulemangejpa.schedule.comment.entity.Comment;
import com.example.schedulemangejpa.schedule.comment.repository.CommentRepository;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import com.example.schedulemangejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public CreateCommentResponseDto createComment(Long scheduleId, String content) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        Author author = authorRepository.findByIdOrElseThrow(schedule.getAuthor().getId());

        Comment comment = new Comment(content);
        comment.setSchedule(schedule);
        comment.setAuthor(author);

        commentRepository.save(comment);

        return new CreateCommentResponseDto(
            comment.getId(),
            comment.getSchedule().getId(),
            comment.getAuthor().getId(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getModifiedAt()
        );
    }


}
