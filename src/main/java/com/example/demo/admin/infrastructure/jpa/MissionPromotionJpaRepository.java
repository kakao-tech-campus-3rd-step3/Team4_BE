package com.example.demo.admin.infrastructure.jpa;

import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionPromotionJpaRepository extends JpaRepository<MissionPromotionEntity, Long> {

    Page<MissionPromotionEntity> findAllByState(Pageable pageable, CustomMissionStateEnum state);
}
