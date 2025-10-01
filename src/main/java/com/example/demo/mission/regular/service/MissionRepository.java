package com.example.demo.mission.regular.service;

import com.example.demo.mission.Mission;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.plan.domain.MissionType;
import java.util.List;
import java.util.Optional;

public interface MissionRepository {

    Optional<Mission> findByIdAndType(Long missionId, MissionType missionType);

    List<RegularMission> findSentimentMissionsAboveAverageByCategory(MissionCategoryEnum category);

    List<RegularMission> findEnergyMissionsAboveAverageByCategory(MissionCategoryEnum category);

    List<RegularMission> findCognitiveMissionsAboveAverageByCategory(MissionCategoryEnum category);

    List<RegularMission> findRelationshipMissionsAboveAverageByCategory(MissionCategoryEnum category);

    List<RegularMission> findStressMissionsAboveAverageByCategory(MissionCategoryEnum category);

    List<RegularMission> findAllByCategory(MissionCategoryEnum missionCategoryEnum);
}
