package com.example.demo.mission.custom.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
//CustomMission을 Mission으로 만드는데 필요한 정보들을 담은 도메인
//이름이 좀 직관적이지 않은듯?
public class CustomMissionStatus {

    @Id
    private Long customMissionId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "custom_mission_id")
    private CustomMissionEntity customMissionEntity;

    @Embedded
    private CustomMissionScore missionScore;

    @Column(nullable = false)
    private Integer level;


}
