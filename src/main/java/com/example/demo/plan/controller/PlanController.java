package com.example.demo.plan.controller;

import com.example.demo.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.mission.regular.service.ActivityService;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.controller.dto.PlanUpdateRequest;
import com.example.demo.plan.controller.dto.TodayPlansResponse;
import com.example.demo.plan.service.PlanService;
import com.example.demo.user.domain.User;
import jakarta.validation.Valid;
import java.net.URI;
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
        @RequestBody @Valid PlanCreateRequest request,
        @CurrentUser User user
    ) {
        activityService.addMissionToPlan(request, user);
        return ResponseEntity.created(URI.create("/api/plans")).build();
    }

    @GetMapping
    public ResponseEntity<TodayPlansResponse> getTodayPlans(
        @CurrentUser User user
    ) {
        TodayPlansResponse response = planService.getTodayPlans(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePlanStatus(
        @PathVariable Long id,
        @RequestBody @Valid PlanUpdateRequest request,
        @CurrentUser User user
    ) {
        activityService.updatePlanStatus(id, request.getIsDone(), user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
        @PathVariable Long id,
        @CurrentUser User user
    ) {
        planService.deletePlan(id, user);
        return ResponseEntity.noContent().build();
    }
}