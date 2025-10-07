package com.example.demo.mission.regular.infrastructure;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionEntity;
import com.example.demo.mission.custom.infrastructure.jpa.CustomMissionJpaRepository;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import com.example.demo.mission.regular.service.MissionRepository;
import com.example.demo.mission.regular.service.score.MissionScoreMinMax;
import com.example.demo.plan.domain.MissionType;
import java.util.List;
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
    public List<RegularMission> findSentimentMissionsAboveAverageByCategory(
            MissionCategoryEnum category) {
        return regularMissionJpaRepository.findSentimentMissionsAboveAverageByCategory(category)
                .stream().map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public List<RegularMission> findEnergyMissionsAboveAverageByCategory(
            MissionCategoryEnum category) {
        return regularMissionJpaRepository.findEnergyMissionsAboveAverageByCategory(category)
                .stream().map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public List<RegularMission> findCognitiveMissionsAboveAverageByCategory(
            MissionCategoryEnum category) {
        return regularMissionJpaRepository.findCognitiveMissionsAboveAverageByCategory(category)
                .stream().map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public List<RegularMission> findRelationshipMissionsAboveAverageByCategory(
            MissionCategoryEnum category) {
        return regularMissionJpaRepository.findRelationshipMissionsAboveAverageByCategory(category)
                .stream().map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public List<RegularMission> findStressMissionsAboveAverageByCategory(
            MissionCategoryEnum category) {
        return regularMissionJpaRepository.findStressMissionsAboveAverageByCategory(category)
                .stream().map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public List<RegularMission> findAllByCategory(MissionCategoryEnum category) {
        return regularMissionJpaRepository.findAllByCategory(category).stream()
                .map(RegularMissionEntity::toModel).toList();
    }

    @Override
    public Optional<MissionScoreMinMax> calculateMissionScoreMinMax() {
        return regularMissionJpaRepository.calculateMissionScoreMinMax();
    }

    @Override
    public MissionScore findMissionScoreByMissionId(Long missionId) {
        return null;
    }
}
