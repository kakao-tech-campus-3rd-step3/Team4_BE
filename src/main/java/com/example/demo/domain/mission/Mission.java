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

@Entity
@Table(name = "missions")
@Getter
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
    private Integer selectionRate;

    @Column(nullable = false)
    private Integer completionRate;

    @Column(name = "mission_level", nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "mission")
    private List<MissionCategoryDetail> missionCategoryDetails = new ArrayList<>();

    protected Mission() {
    }

}
