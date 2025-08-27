package com.example.demo.domain.diary;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "diary_feedback")
public class DiaryFeedback extends BaseEntity {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    protected DiaryFeedback() {
    }

    private DiaryFeedback(Diary diary, String content) {
        this.diary = diary;
        this.content = content;
    }

    public static DiaryFeedback of(Diary diary, String content) {
        return new DiaryFeedback(diary, content);
    }
}
