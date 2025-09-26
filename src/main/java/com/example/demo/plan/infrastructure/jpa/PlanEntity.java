package com.example.demo.plan.infrastructure.jpa;

import com.example.demo.common.infrastructure.jpa.BaseEntity;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
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
@Table(name = "plan")
public class PlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long missionId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private MissionCategoryEnum category;

    @Column(nullable = false)
    private Boolean isDone = false;

    protected PlanEntity() {
    }

    private PlanEntity(Long id, MissionType missionType, Long userId, Long missionId, String content,
        MissionCategoryEnum category, Boolean isDone) {
        this.id = id;
        this.missionType = missionType;
        this.userId = userId;
        this.missionId = missionId;
        this.content = content;
        this.category = category;
        this.isDone = isDone;
    }

    public static PlanEntity fromModel(Plan plan) {
        return new PlanEntity(plan.getId(), plan.getMissionType(), plan.getUserId(),
            plan.getMissionId(), plan.getContent(),
            plan.getCategory(), plan.isDone());
    }

    public Plan toModel() {
        return new Plan(id, missionType, userId, missionId, content, category, isDone);
    }
}