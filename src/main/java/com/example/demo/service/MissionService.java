package com.example.demo.service;

import com.example.demo.dto.mission.MissionResponse;
import com.example.demo.repository.MissionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<MissionResponse> getRecommendedMissions() {
        return missionRepository.findAll().stream()
            .map(MissionResponse::new)
            .collect(Collectors.toList());
    }
}