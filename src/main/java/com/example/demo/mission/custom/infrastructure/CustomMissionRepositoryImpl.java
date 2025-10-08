package com.example.demo.mission.custom.infrastructure;

import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionEntity;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionJpaRepository;
import com.example.demo.mission.custom.service.CustomMissionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMissionRepositoryImpl implements CustomMissionRepository {

    private final CustomMissionJpaRepository customMissionJpaRepository;

    @Override
    public CustomMission save(CustomMission customMission) {
        CustomMissionEntity entity = CustomMissionEntity.fromModel(customMission);
        return customMissionJpaRepository.save(entity).toModel();
    }

    @Override
    public Optional<CustomMission> findById(Long id) {
        return customMissionJpaRepository.findById(id)
            .map(CustomMissionEntity::toModel);
    }

}
