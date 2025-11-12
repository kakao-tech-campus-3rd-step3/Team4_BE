package com.example.demo.mission.controller;

import com.example.demo.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.mission.controller.dto.CustomMissionCreateRequest;
import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.service.CustomMissionService;
import com.example.demo.mission.regular.service.ActivityService;
import com.example.demo.user.domain.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final CustomMissionService customMissionService;
    private final ActivityService activityService;

    @PostMapping("/custom")
    public ResponseEntity<MissionResponse> createCustomMission(
        @RequestBody @Valid CustomMissionCreateRequest request,
        @CurrentUser User user
    ) {
        CustomMission customMission = customMissionService.create(
            request.getContent(),
            request.getCategory(),
            user
        );

        return ResponseEntity.ok(new MissionResponse(customMission));
    }

    @PutMapping("/custom/{missionId}")
    public ResponseEntity<MissionResponse> updateCustomMission(
        @PathVariable Long missionId,
        @RequestBody @Valid CustomMissionCreateRequest request,
        @CurrentUser User user
    ) {
        CustomMission customMission = customMissionService.update(
            missionId,
            request.getContent(),
            request.getCategory(),
            user
        );

        return ResponseEntity.ok(new MissionResponse(customMission));
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getRecommendedMissions(@CurrentUser User user) {
        return ResponseEntity.ok(activityService.getRecommendedMissions(user));
    }

}
