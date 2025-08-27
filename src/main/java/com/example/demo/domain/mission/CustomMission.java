package com.example.demo.domain.mission;

import com.example.demo.domain.User;
import com.example.demo.domain.common.Category;
import com.example.demo.domain.common.CustomMissionState;
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
    private Category category;

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
    private CustomMissionState state;

    protected CustomMission() {
    }

}
