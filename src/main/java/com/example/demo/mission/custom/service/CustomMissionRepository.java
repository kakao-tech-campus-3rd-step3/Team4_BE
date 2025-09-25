package com.example.demo.mission.custom.service;

import com.example.demo.mission.custom.domain.CustomMission;
import java.util.Optional;

public interface CustomMissionRepository {

    CustomMission save(CustomMission customMission);

    Optional<CustomMission> findById(Long missionId);

}
