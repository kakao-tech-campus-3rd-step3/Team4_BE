package com.example.demo.plan.infrastructure.jpa;

import com.example.demo.mission.controller.dto.MissionCompletionCount;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {

    List<PlanEntity> findByUserIdAndCreatedAtBetween(Long userId,
        LocalDateTime startOfDay, LocalDateTime endOfDay);


    @Query(
        "select new com.example.demo.mission.controller.dto.MissionCompletionCount(m.category, count(m)) "
            + "from PlanEntity p join RegularMissionEntity m on p.missionId = m.id "
            + "where p.userId = :userId and p.isDone = true "
            + "group by m.category")
    List<MissionCompletionCount> findCompletedMissionCount(@Param("userId") Long userId);

}
