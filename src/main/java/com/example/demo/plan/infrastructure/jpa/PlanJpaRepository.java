package com.example.demo.plan.infrastructure.jpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {

    List<PlanEntity> findByUserIdAndCreatedAtBetween(Long userId,
        LocalDateTime startOfDay, LocalDateTime endOfDay);
}
