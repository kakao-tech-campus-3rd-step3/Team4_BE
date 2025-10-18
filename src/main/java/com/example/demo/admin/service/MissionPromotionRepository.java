package com.example.demo.admin.service;

import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MissionPromotionRepository {

    Optional<MissionPromotion> findById(Long id);

    Page<MissionPromotion> findAllByState(Pageable pageable, CustomMissionStateEnum state);

    MissionPromotion save(MissionPromotion adminCustomMission);

}
