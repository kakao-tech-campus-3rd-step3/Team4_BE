package com.example.demo.mission.custom.infrastructure.jpa;

import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.user.infrastructure.jpa.UserEntity;
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

//CustomMission은 사용자가 작성한 내용만 저장,
//일반 미션으로의 승격이 필요한 데이터는 CustomMissionStatus에서 관리
public class CustomMissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomMissionStateEnum state;

    protected CustomMissionEntity() {
    }

    private CustomMissionEntity(Long id, UserEntity author, String content, MissionCategoryEnum category, CustomMissionStateEnum state) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.category = category;
        this.state = state;
    }

    public static CustomMissionEntity fromModel(CustomMission customMission) {
        return new CustomMissionEntity(customMission.getId(), UserEntity.fromModel(customMission.getAuthor()), customMission.getContent(), customMission.getCategory(), customMission.getState());
    }

    public CustomMission toModel() {
        return new CustomMission(id, content, category, author.toModel(), state);
    }

}
