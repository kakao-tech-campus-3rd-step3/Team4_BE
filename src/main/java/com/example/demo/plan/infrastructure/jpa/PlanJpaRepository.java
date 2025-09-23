package com.example.demo.plan.infrastructure.jpa;

import com.example.demo.user.infrastructure.jpa.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<PlanEntity, Long> {

    List<PlanEntity> findByUserEntityAndCreatedAtBetween(UserEntity userEntity,
        LocalDateTime startOfDay, LocalDateTime endOfDay);
}
