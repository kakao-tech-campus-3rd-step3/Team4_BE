package com.example.demo.mission.regular.service;

import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.regular.service.counter.MissionCounterService;
import com.example.demo.mission.regular.service.recommend.MissionRecommendService;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.service.PlanInternalService;
import com.example.demo.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final MissionRecommendService missionRecommendService;
    private final PlanInternalService planInternalService;
    private final MissionCounterService missionCounterService;
    private final MissionCompletionEmotionService missionCompletionEmotionService;

    public void addMissionToPlan(PlanCreateRequest request, User user) {
        Long missionId = planInternalService.addMissionToPlan(request, user);
        try {
            missionCounterService.addSelectionDelta(missionId);
        } catch (Exception e) {
            log.error("Selection delta update failed for missionId: {}", missionId, e);
        }
    }

    public void updatePlanStatus(Long planId, boolean isDone, User user) {
        Plan plan = planInternalService.updatePlanStatus(planId, isDone, user);
        if (isDone) {
            try {
                missionCounterService.addCompletionDelta(plan.getMissionId());
            } catch (Exception e) {
                log.error("Completion delta update failed for missionId: {}", plan.getMissionId(),
                        e);
            }
            if (plan.getMissionType() == MissionType.REGULAR) {
                missionCompletionEmotionService.updateEmotionOnMissionComplete(user,
                    plan.getMissionId());
            }
        }
    }

    public List<MissionResponse> getRecommendedMissions(User user) {
        List<MissionResponse> recommend = missionRecommendService.getRecommendedMissions(
                user);
        List<Long> ids = recommend.stream().map(MissionResponse::getId).toList();
        try {
            missionCounterService.addExposureDelta(ids);
        } catch (Exception e) {
            log.error("Exposure delta update failed for missions: {}", ids, e);
        }
        return recommend;
    }

}
