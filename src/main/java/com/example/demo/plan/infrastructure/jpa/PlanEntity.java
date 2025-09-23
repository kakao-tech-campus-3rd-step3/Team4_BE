package com.example.demo.plan.infrastructure.jpa;

import com.example.demo.common.infrastructure.jpa.BaseEntity;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "plan")
//content, category, isDone만 있어도 충분하다고 생각
//mission, customMission을 연관관계로 맺고있게 되면 미션을 삭제해야 하는 일이 생길 때 지장이 있음
//mission또는 customMission의 정보를 받아와서 객체를 생성하는 것이 올바르다
//UserMission이라는 이름이 명확하지 않아 Plan으로 변경
public class PlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

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

    public PlanEntity(Long id, MissionType missionType, UserEntity userEntity, Long missionId, String content, MissionCategoryEnum category, Boolean isDone) {
        this.id = id;
        this.missionType = missionType;
        this.userEntity = userEntity;
        this.missionId = missionId;
        this.content = content;
        this.category = category;
        this.isDone = isDone;
    }

    public static PlanEntity fromModel(Plan plan) {
        return new PlanEntity(plan.getId(), plan.getMissionType(), UserEntity.fromModel(plan.getUser()),
            plan.getMissionId(), plan.getContent(),
            plan.getCategory(), plan.isDone());
    }

    public Plan toModel() {
        return new Plan(id, missionType, userEntity.toModel(), missionId, content, category, isDone);
    }

    public void updateDone(Boolean done) {
        this.isDone = done;
    }
}