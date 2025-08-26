package com.example.demo.domain.diary;

import com.example.demo.domain.BaseEntity;
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
    private String feedback; //content?

    protected DiaryFeedback() {
    }

    private DiaryFeedback(Diary diary, String feedback) {
        this.diary = diary;
        this.feedback = feedback;
    }

    public static DiaryFeedback of(Diary diary, String feedback) {
        return new DiaryFeedback(diary, feedback);
    }
}
