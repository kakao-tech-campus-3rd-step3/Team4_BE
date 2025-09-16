package com.example.demo.repository;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.dto.mission.MissionAverageResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(
            "select new com.example.demo.dto.mission.MissionAverageResponse(" +
                    "avg(m.sentimentScore), " +
                    "avg(m.energyScore), " +
                    "avg(m.cognitiveScore), " +
                    "avg(m.relationshipScore), " +
                    "avg(m.stressScore)" +
                    ") " +
                    "from Mission m " +
                    "where m.category = :category"
    )
    MissionAverageResponse findAverageScoreByCategory(
            @Param("category") MissionCategoryEnum category);

    List<Mission> findByCategoryAndSentimentScoreGreaterThanEqual(MissionCategoryEnum category,
            Integer threshold);

    List<Mission> findByCategoryAndEnergyScoreGreaterThanEqual(MissionCategoryEnum category,
            Integer threshold);

    List<Mission> findByCategoryAndCognitiveScoreGreaterThanEqual(MissionCategoryEnum category,
            Integer threshold);

    List<Mission> findByCategoryAndRelationshipScoreGreaterThanEqual(MissionCategoryEnum category,
            Integer threshold);

    List<Mission> findByCategoryAndStressScoreGreaterThanEqual(MissionCategoryEnum category,
            Integer threshold);

    List<Mission> findAllByCategory(MissionCategoryEnum category);

    @Modifying
    @Query("update Mission m set m.exposureCount = m.exposureCount + 1 where m.id in :missionIds")
    void incrementExposure(@Param("missionIds") List<Long> missionIds);

    @Modifying
    @Query("update Mission m set m.selectionCount = m.selectionCount + 1 where m.id = :missionId")
    void incrementSelection(@Param("missionId") Long missionId);

    @Modifying
    @Query("update Mission m set m.completionCount = m.completionCount + 1 where m.id = :missionId")
    void incrementCompletion(@Param("missionId") Long missionId);

}
