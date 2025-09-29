package com.example.demo.plan.controller;

import com.example.demo.common.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.mission.regular.service.ActivityService;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.controller.dto.PlanUpdateRequest;
import com.example.demo.plan.controller.dto.TodayPlansResponse;
import com.example.demo.plan.service.PlanService;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<Void> addMissionToPlan(
        @RequestBody PlanCreateRequest request,
        @CurrentUser User user
    ) {
        activityService.addMissionToPlan(request, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<TodayPlansResponse> getTodayPlans(
        @CurrentUser User user
    ) {
        TodayPlansResponse todayPlans = planService.getTodayPlans(user);
        return ResponseEntity.ok(todayPlans);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<Void> updatePlanStatus(
        @PathVariable Long planId,
        @RequestBody PlanUpdateRequest request,
        @CurrentUser User user
    ) {
        activityService.updatePlanStatus(planId, request.isDone(), user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
        @PathVariable Long planId,
        @CurrentUser User user
    ) {
        planService.deletePlan(planId, user);
        return ResponseEntity.ok().build();
    }

}
