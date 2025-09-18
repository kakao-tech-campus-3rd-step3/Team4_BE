package com.example.demo.domain.mission;

import com.example.demo.domain.category.MissionCategoryDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Column(nullable = false)
    private Integer sentimentScore;

    @Column(nullable = false)
    private Integer energyScore;

    @Column(nullable = false)
    private Integer cognitiveScore;

    @Column(nullable = false)
    private Integer relationshipScore;

    @Column(nullable = false)
    private Integer stressScore;

    @Column(nullable = false)
    private Integer employmentScore;

    @Column(nullable = false)
    private Integer exposureCount;

    @Column(nullable = false)
    private Integer selectionCount;

    @Column(nullable = false)
    private Integer completionCount;

    @Column(name = "mission_level", nullable = false)
    private Integer missionLevel;

    @OneToMany(mappedBy = "mission")
    private List<MissionCategoryDetail> missionCategoryDetails = new ArrayList<>();

    protected Mission() {
    }

    public Mission(String content, MissionCategoryEnum category, Integer sentimentScore,
        Integer energyScore, Integer cognitiveScore, Integer relationshipScore, Integer stressScore,
        Integer employmentScore, Integer exposureCount, Integer selectionCount,
        Integer completionCount,
        Integer missionLevel) {
        this.content = content;
        this.category = category;
        this.sentimentScore = sentimentScore;
        this.energyScore = energyScore;
        this.cognitiveScore = cognitiveScore;
        this.relationshipScore = relationshipScore;
        this.stressScore = stressScore;
        this.employmentScore = employmentScore;
        this.exposureCount = exposureCount;
        this.selectionCount = selectionCount;
        this.completionCount = completionCount;
        this.missionLevel = missionLevel;
    }
}
