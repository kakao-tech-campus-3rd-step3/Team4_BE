package com.example.demo.repository;

import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.dto.mission.MissionCountResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission> findByUser(User user);

    @Query("SELECT um FROM UserMission um " +
        "WHERE um.user = :user " +
        "AND um.createdAt >= :startOfDay AND um.createdAt < :endOfDay " +
        "AND (:isDone IS NULL OR um.done = :isDone)")
    List<UserMission> findByUserAndDateAndDone(@Param("user") User user,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay, @Param("isDone") Boolean isDone);

    boolean existsByUserAndMissionIdAndDoneIsFalseAndCreatedAtBetween(User user, Long missionId,
        LocalDateTime startOfDay, LocalDateTime endOfDay);


    @Query("select new com.example.demo.dto.mission.MissionCountResponse(m.category, count(m)) " +
            "from UserMission um join um.mission m on um.mission.id = m.id " +
            "where um.user.id = :userId " +
            "group by m.category")
    List<MissionCountResponse> countByCategory(@Param("userId") Long userId);

}