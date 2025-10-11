package com.example.demo.plan.service;

import com.example.demo.mission.controller.dto.MissionCompletionCount;
import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.domain.TodayPlans;
import com.example.demo.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    TodayPlans findTodayPlans(User user);

    void saveAll(List<Plan> plans);

    Plan save(Plan plan);

    void deleteById(Long planId);

    Optional<Plan> findById(Long planId);

    List<MissionCompletionCount> findCompletedMissionCount(Long id);
}
