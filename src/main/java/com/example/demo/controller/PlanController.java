package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.user.User;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.service.PlanCommandService;
import com.example.demo.service.PlanQueryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanCommandService planCommandService;
    private final PlanQueryService planQueryService;

    @PostMapping
    public ResponseEntity<Void> addPlan(@RequestBody AddPlanRequest request,
        @CurrentUser User user) {
        planCommandService.addMissionToPlan(request.getMissionId(), user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getPlans(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required = false) Boolean isDone,
        @CurrentUser User user) {
        return ResponseEntity.ok(planQueryService.getPlans(date, isDone, user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePlanStatus(@PathVariable Long id,
        @RequestBody UpdatePlanRequest request, @CurrentUser User user) {
        planCommandService.updatePlanStatus(id, request.getIsDone(), user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id, @CurrentUser User user) {
        planCommandService.deletePlan(id, user);
        return ResponseEntity.noContent().build();
    }
}