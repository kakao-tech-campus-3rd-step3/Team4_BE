package com.example.demo.common.admin.service;

import com.example.demo.common.admin.domain.MissionPromotion;
import java.util.List;

public interface MissionPromotionRepository {

    List<MissionPromotion> findAll();

    MissionPromotion save(MissionPromotion adminCustomMission);

}
