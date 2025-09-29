package com.example.demo.mission.custom.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
//CustomMission을 Mission으로 만드는데 필요한 정보들을 담은 도메인
//이름이 좀 직관적이지 않은듯?
public class CustomMissionStatus {

    @Id
    private Long customMissionId;

    @Embedded
    private CustomMissionScore missionScore;

    @Column(nullable = false)
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomMissionStateEnum state;

    public CustomMissionStatus(Long customMissionId, CustomMissionScore missionScore, Integer level,
        CustomMissionStateEnum state) {
        this.customMissionId = customMissionId;
        this.missionScore = missionScore;
        this.level = level;
        this.state = state;
    }

    protected CustomMissionStatus() {
    }
}
