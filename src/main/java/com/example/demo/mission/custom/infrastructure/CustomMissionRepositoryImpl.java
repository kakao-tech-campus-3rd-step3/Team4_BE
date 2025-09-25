package com.example.demo.mission.custom.infrastructure;

import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionEntity;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionJpaRepository;
import com.example.demo.mission.custom.service.CustomMissionRepository;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMissionRepositoryImpl implements CustomMissionRepository {

    private final CustomMissionJpaRepository customMissionJpaRepository;

    @Override
    public CustomMission save(CustomMission customMission, UserEntity user) {
        CustomMissionEntity customMissionEntity = CustomMissionEntity.fromModel(customMission,
            user);
        CustomMissionEntity savedEntity = customMissionJpaRepository.save(customMissionEntity);
        return savedEntity.toModel();
    }

    @Override
    public Optional<CustomMission> findById(Long id) {
        return customMissionJpaRepository.findById(id)
            .map(CustomMissionEntity::toModel);
    }

}
