package com.example.demo.plan.service;

import com.example.demo.mission.Mission;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.domain.TodayPlans;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService, PlanInternalService {

    private final PlanRepository planRepository;
    private final MissionRepository missionRepository;

    public Long addMissionToPlan(PlanCreateRequest request, User user) {
        Mission mission = missionRepository.findByIdAndType(request.getMissionId(),
            request.getMissionType()).orElseThrow(() -> new RuntimeException("Mission Not Found"));
        TodayPlans todayPlans = planRepository.findTodayPlans(user);
        todayPlans.addMission(mission);
        planRepository.saveAll(todayPlans.getPlans());
        return mission.getId();
    }

    public Plan updatePlanStatus(Long planId, boolean isDone, User user) {
        TodayPlans todayPlans = planRepository.findTodayPlans(user);
        Plan plan = todayPlans.updateDone(planId, isDone);
        planRepository.saveAll(todayPlans.getPlans());
        return plan;
    }

    public void deletePlan(Long planId, User user) {
        TodayPlans todayPlans = planRepository.findTodayPlans(user);
        todayPlans.deletePlan(planId);
        planRepository.deleteById(planId);
    }

    @Override
    @Transactional(readOnly = true)
    public TodayPlans getTodayPlans(User user) {
        return planRepository.findTodayPlans(user);
    }

}
