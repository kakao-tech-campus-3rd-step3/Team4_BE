package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.user.User;
import com.example.demo.dto.mission.MissionResponse;
import com.example.demo.service.MissionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getRecommendedMissions(@CurrentUser User user) {
        return ResponseEntity.ok(missionService.getRecommendedMissions(user));
    }
}