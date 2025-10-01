package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.MissionCategoryEnum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegularMissionJpaRepository extends JpaRepository<RegularMissionEntity, Long> {

    @Query("""
        SELECT m
        FROM RegularMissionEntity m
        WHERE m.category = :category
          AND m.missionScoreEmbeddable.sentimentScore >= (
              SELECT AVG(m2.missionScoreEmbeddable.sentimentScore)
              FROM RegularMissionEntity m2
              WHERE m2.category = :category
          )
    """)
    List<RegularMissionEntity> findSentimentMissionsAboveAverageByCategory(@Param("category") MissionCategoryEnum category);

    @Query("""
        SELECT m
        FROM RegularMissionEntity m
        WHERE m.category = :category
          AND m.missionScoreEmbeddable.energyScore >= (
              SELECT AVG(m2.missionScoreEmbeddable.energyScore)
              FROM RegularMissionEntity m2
              WHERE m2.category = :category
          )
    """)
    List<RegularMissionEntity> findEnergyMissionsAboveAverageByCategory(@Param("category") MissionCategoryEnum category);

    @Query("""
        SELECT m
        FROM RegularMissionEntity m
        WHERE m.category = :category
          AND m.missionScoreEmbeddable.cognitiveScore >= (
              SELECT AVG(m2.missionScoreEmbeddable.cognitiveScore)
              FROM RegularMissionEntity m2
              WHERE m2.category = :category
          )
    """)
    List<RegularMissionEntity> findCognitiveMissionsAboveAverageByCategory(@Param("category") MissionCategoryEnum category);

    @Query("""
        SELECT m
        FROM RegularMissionEntity m
        WHERE m.category = :category
          AND m.missionScoreEmbeddable.relationshipScore >= (
              SELECT AVG(m2.missionScoreEmbeddable.relationshipScore)
              FROM RegularMissionEntity m2
              WHERE m2.category = :category
          )
    """)
    List<RegularMissionEntity> findRelationshipMissionsAboveAverageByCategory(@Param("category") MissionCategoryEnum category);

    @Query("""
        SELECT m
        FROM RegularMissionEntity m
        WHERE m.category = :category
          AND m.missionScoreEmbeddable.stressScore >= (
              SELECT AVG(m2.missionScoreEmbeddable.stressScore)
              FROM RegularMissionEntity m2
              WHERE m2.category = :category
          )
    """)
    List<RegularMissionEntity> findStressMissionsAboveAverageByCategory(@Param("category") MissionCategoryEnum category);

    List<RegularMissionEntity> findAllByCategory(MissionCategoryEnum category);
}
