package com.example.schedulemangejpa.schedule.entity;

import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.common.entity.BaseEntity;
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
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String content;

    //author 테이블과 다대일 관계를 갖도록 함. 연관관계 설정
    @Setter
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Schedule() {
    }

    public Schedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
