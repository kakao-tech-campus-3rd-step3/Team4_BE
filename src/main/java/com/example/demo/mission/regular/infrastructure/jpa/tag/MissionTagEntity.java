package com.example.demo.mission.regular.infrastructure.jpa.tag;

import com.example.demo.mission.regular.domain.MissionTag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mission_tag")
//tag의 정보를 가져와 mission_tag로 생성
public class MissionTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected MissionTagEntity() {
    }

    private MissionTagEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MissionTagEntity(String name) {
        this.name = name;
    }

    public MissionTag toModel() {
        return new MissionTag(id, name);
    }

    public static MissionTagEntity fromModel(MissionTag missionTag) {
        return new MissionTagEntity(missionTag.getId(), missionTag.getName());
    }
}
