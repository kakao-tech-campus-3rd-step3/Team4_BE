package com.example.demo.plan.controller;

import com.example.demo.common.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.controller.dto.PlanUpdateRequest;
import com.example.demo.plan.controller.dto.TodayPlansResponse;
import com.example.demo.plan.domain.TodayPlans;
import com.example.demo.plan.service.PlanInternalService;
import com.example.demo.plan.service.PlanService;
import com.example.demo.user.domain.User;
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
    private final PlanInternalService planInternalService;

    @PostMapping
    public ResponseEntity<Void> addMissionToPlan(
        @RequestBody PlanCreateRequest request,
        @CurrentUser User user
    ) {
        Long planId = planInternalService.addMissionToPlan(request, user);
        return ResponseEntity.created(URI.create("/api/plans/" + planId)).build();
    }

    @GetMapping
    public ResponseEntity<TodayPlansResponse> getTodayPlans(
        @CurrentUser User user
    ) {
        TodayPlans todayPlans = planService.getTodayPlans(user);
        return ResponseEntity.ok(new TodayPlansResponse(todayPlans));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePlanStatus(
        @PathVariable Long id,
        @RequestBody PlanUpdateRequest request,
        @CurrentUser User user
    ) {
        planInternalService.updatePlanStatus(id, request.getIsDone(), user);
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