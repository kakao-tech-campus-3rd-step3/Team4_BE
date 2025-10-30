package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.mission.regular.infrastructure.jpa.tag.MissionTagEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
public class RegularMissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Column(name = "mission_level", nullable = false)
    private Integer missionLevel;

    @Embedded
    private MissionScoreEmbeddable missionScoreEmbeddable;

    @Embedded
    private MissionCountEmbeddable missionCountEmbeddable;

    @OneToMany
    private List<MissionTagEntity> missionTagEntities = new ArrayList<>();

    protected RegularMissionEntity() {
    }

    public RegularMissionEntity(
            Long id,
            String content,
            MissionCategoryEnum category,
            Integer missionLevel,
            MissionScoreEmbeddable missionScoreEmbeddable,
            MissionCountEmbeddable missionCountEmbeddable,
            List<MissionTagEntity> missionTagEntities
    ) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.missionLevel = missionLevel;
        this.missionScoreEmbeddable = missionScoreEmbeddable;
        this.missionCountEmbeddable = missionCountEmbeddable;
        this.missionTagEntities = missionTagEntities;
    }

    public RegularMission toModel() {
        return new RegularMission(id, content, category);
    }
}
