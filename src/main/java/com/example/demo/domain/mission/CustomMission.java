package com.example.demo.domain.mission;

import com.example.demo.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "custom_missions")
public class CustomMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Column
    private Integer sentimentScore;

    @Column
    private Integer energyScore;

    @Column
    private Integer cognitiveScore;

    @Column
    private Integer relationshipScore;

    @Column
    private Integer stressScore;

    @Column
    private Integer employmentScore;

    @Column
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomMissionStateEnum state;

    protected CustomMission() {
    }

    public CustomMission(User author, String content, MissionCategoryEnum category,
            CustomMissionStateEnum state) {
        this.author = author;
        this.content = content;
        this.category = category;
        this.state = state;
    }

    public void update(String content, MissionCategoryEnum category) {
        this.content = content;
        this.category = category;
    }
}
