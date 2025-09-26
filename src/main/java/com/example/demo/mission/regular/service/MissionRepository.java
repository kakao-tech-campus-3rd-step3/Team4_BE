package com.example.demo.mission.regular.service;

import com.example.demo.mission.Mission;
import com.example.demo.plan.domain.MissionType;

import java.util.Optional;

public interface MissionRepository {

    Optional<Mission> findByIdAndType(Long missionId, MissionType missionType);

}
