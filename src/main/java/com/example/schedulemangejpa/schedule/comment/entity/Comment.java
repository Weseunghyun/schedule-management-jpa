package com.example.schedulemangejpa.schedule.comment.entity;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.common.entity.BaseEntity;
import com.example.schedulemangejpa.schedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; //스케줄과의 연관관계 추가

    @Setter
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author; // 작성자와의 연관관계 추가

    public Comment() {

    }

    public Comment(String content) {
        this.content = content;
    }
}
