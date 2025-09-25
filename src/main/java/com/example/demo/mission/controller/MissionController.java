package com.example.demo.mission.controller;

import com.example.demo.mission.controller.dto.CustomMissionCreateRequest;
import com.example.demo.mission.controller.dto.MissionResponse;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.service.CustomMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final CustomMissionService customMissionService;

    @PostMapping("/custom")
    public ResponseEntity<MissionResponse> createCustomMission(
        @RequestBody CustomMissionCreateRequest request,
        @RequestParam("userId") Long userId // 임시로 userId를 RequestParam으로 받도록 설정
        // @CurrentUser User user
    ) {
        CustomMission customMission = customMissionService.create(request, userId);
        return ResponseEntity.ok(new MissionResponse(customMission));
    }

    @PutMapping("/custom/{missionId}")
    public ResponseEntity<MissionResponse> updateCustomMission(
        @PathVariable Long missionId,
        @RequestBody CustomMissionCreateRequest request,
        @RequestParam("userId") Long userId // 임시로 userId를 RequestParam으로 받도록 설정
        // @CurrentUser User user
    ) {
        CustomMission customMission = customMissionService.update(missionId, request, userId);
        return ResponseEntity.ok(new MissionResponse(customMission));
    }
}