package com.example.demo.mission.regular.infrastructure;

import com.example.demo.mission.Mission;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionEntity;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionJpaRepository;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.plan.domain.MissionType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepository {

    private final CustomMissionJpaRepository customMissionJpaRepository;
    private final RegularMissionJpaRepository regularMissionJpaRepository;

    @Override
    public Optional<Mission> findByIdAndType(Long missionId, MissionType missionType) {
        if (missionType == MissionType.REGULAR) {
            return regularMissionJpaRepository.findById(missionId)
                    .map(RegularMissionEntity::toModel);
        } else {
            return customMissionJpaRepository.findById(missionId).map(CustomMissionEntity::toModel);
        }
    }

    @Override
    public MissionScore findMissionScoreByMissionId(Long missionId) {
        return null;
    }
}
