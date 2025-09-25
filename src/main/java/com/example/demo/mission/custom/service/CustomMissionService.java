package com.example.demo.mission.custom.service;

import com.example.demo.mission.controller.dto.CustomMissionCreateRequest;
import com.example.demo.mission.custom.domain.CustomMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomMissionService {

    private final CustomMissionRepository customMissionRepository;

    public CustomMission create(CustomMissionCreateRequest request, Long userId) {
        CustomMission customMission = CustomMission.create(
            request.getContent(),
            request.getCategory(),
            userId
        );
        return customMissionRepository.save(customMission);
    }

    public CustomMission update(Long missionId, CustomMissionCreateRequest request, Long userId) {
        CustomMission customMission = customMissionRepository.findById(missionId)
            .orElseThrow(() -> new RuntimeException("해당 미션을 찾을 수 없습니다."));

        customMission.validateAuthor(userId);
        customMission.update(request.getContent(), request.getCategory());

        return customMissionRepository.save(customMission);
    }
}