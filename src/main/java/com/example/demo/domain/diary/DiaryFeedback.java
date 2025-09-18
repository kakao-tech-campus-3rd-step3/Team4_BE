package com.example.demo.domain.diary;

import com.example.demo.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
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

    public DiaryFeedback(Diary diary, String content) {
        this.diary = diary;
        this.content = content;
    }

}
