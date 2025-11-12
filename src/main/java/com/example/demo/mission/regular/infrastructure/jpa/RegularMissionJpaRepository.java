package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.infrastructure.MissionScoreMinMax;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
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
    List<RegularMissionEntity> findSentimentMissionsAboveAverageByCategory(
            @Param("category") MissionCategoryEnum category);

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
    List<RegularMissionEntity> findEnergyMissionsAboveAverageByCategory(
            @Param("category") MissionCategoryEnum category);

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
    List<RegularMissionEntity> findCognitiveMissionsAboveAverageByCategory(
            @Param("category") MissionCategoryEnum category);

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
    List<RegularMissionEntity> findRelationshipMissionsAboveAverageByCategory(
            @Param("category") MissionCategoryEnum category);

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
    List<RegularMissionEntity> findStressMissionsAboveAverageByCategory(
            @Param("category") MissionCategoryEnum category);

    @Query("""
                SELECT m
                FROM RegularMissionEntity m
                WHERE m.category = :category
                  AND m.missionScoreEmbeddable.employmentScore BETWEEN :minScore AND :maxScore
            """)
    List<RegularMissionEntity> findByCategoryAndEmploymentScoreBetween(
            @Param("category") MissionCategoryEnum category,
            @Param("minScore") int minScore,
            @Param("maxScore") int maxScore
    );

    List<RegularMissionEntity> findAllByCategory(MissionCategoryEnum category);

    @Query("""
                SELECT NEW com.example.demo.mission.regular.infrastructure.MissionScoreMinMax (
                    MIN(m.missionScoreEmbeddable.sentimentScore), MAX(m.missionScoreEmbeddable.sentimentScore),
                    MIN(m.missionScoreEmbeddable.energyScore), MAX(m.missionScoreEmbeddable.energyScore),
                    MIN(m.missionScoreEmbeddable.cognitiveScore), MAX(m.missionScoreEmbeddable.cognitiveScore),
                    MIN(m.missionScoreEmbeddable.relationshipScore), MAX(m.missionScoreEmbeddable.relationshipScore),
                    MIN(m.missionScoreEmbeddable.stressScore), MAX(m.missionScoreEmbeddable.stressScore),
                    MIN(m.missionScoreEmbeddable.employmentScore), MAX(m.missionScoreEmbeddable.employmentScore)
                )
                FROM RegularMissionEntity m
            """)
    Optional<MissionScoreMinMax> calculateMissionScoreMinMax();

    List<RegularMissionEntity> findAllByCategory(MissionCategoryEnum category, Sort sort);
}
