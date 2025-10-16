package com.example.demo.mission.custom.infrastructure.jpa;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "custom_missions")

//CustomMission은 사용자가 작성한 내용만 저장,
//일반 미션으로의 승격이 필요한 데이터는 CustomMissionStatus에서 관리
public class CustomMissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    protected CustomMissionEntity() {
    }

    private CustomMissionEntity(Long id, Long userId, String content,
            MissionCategoryEnum category) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.category = category;
    }

    public static CustomMissionEntity fromModel(CustomMission customMission) {
        return new CustomMissionEntity(customMission.getId(), customMission.getUserId(),
                customMission.getContent(), customMission.getCategory());
    }

    public CustomMission toModel() {
        return new CustomMission(id, content, category, userId);
    }

}
