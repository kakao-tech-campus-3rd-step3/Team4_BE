package com.example.demo.mission;

import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;

public interface Mission {
    Long getId();
    String getContent();
    MissionCategoryEnum getCategory();

    MissionType getMissionType();

    Plan toPlan(Long userId);
}
