package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.user.User;
import com.example.demo.dto.mission.CustomMissionRequest;
import com.example.demo.dto.mission.CustomMissionResponse;
import com.example.demo.service.CustomMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/custom-missions")
public class CustomMissionController {

    private final CustomMissionService customMissionService;

    @PostMapping
    public ResponseEntity<CustomMissionResponse> create(@RequestBody CustomMissionRequest request,
        @CurrentUser User user) {
        CustomMission savedCustomMission = customMissionService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CustomMissionResponse.from(savedCustomMission));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
        @RequestBody CustomMissionRequest request, @CurrentUser User user) {
        customMissionService.update(id, request, user);
        return ResponseEntity.ok().build();
    }
}
