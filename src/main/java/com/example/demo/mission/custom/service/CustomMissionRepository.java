package com.example.demo.mission.custom.service;

import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import java.util.Optional;

public interface CustomMissionRepository {

    CustomMission save(CustomMission customMission, UserEntity user);

    Optional<CustomMission> findById(Long id);

}
