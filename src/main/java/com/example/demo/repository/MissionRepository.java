package com.example.demo.repository;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.userEmotion.UserEmotionTypeEnum;
import com.example.demo.dto.mission.MissionAverageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
    MissionAverageResponse findAverageScoreByCategory(@Param("category") MissionCategoryEnum category);

    @Query(value = """
                SELECT * FROM mission m
                WHERE m.category = :category
                  AND (
                    (:emotion = 'SENTIMENT' AND m.sentiment_level > :avg)
                    OR (:emotion = 'ENERGY' AND m.energy_level > :avg)
                    OR (:emotion = 'COGNITIVE' AND m.cognitive_level > :avg)
                    OR (:emotion = 'RELATIONSHIP' AND m.relationship_level > :avg)
                    OR (:emotion = 'STRESS' AND m.stress_level > :avg)
                  )
            """, nativeQuery = true)
    List<Mission> findMissions(
            @Param("category") String category,
            @Param("emotion") String emotion,
            @Param("avg") Double avg
    );
}
