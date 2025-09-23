package com.example.demo.mission;

import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.user.domain.User;

public interface Mission {
    Long getId();
    String getContent();
    MissionCategoryEnum getCategory();

    MissionType getMissionType();

    Plan toPlan(User user);
}
