package com.example.demo.common.admin.infrastructure;

import com.example.demo.common.admin.domain.MissionPromotion;
import com.example.demo.common.admin.infrastructure.jpa.MissionPromotionEntity;
import com.example.demo.common.admin.infrastructure.jpa.MissionPromotionJpaRepository;
import com.example.demo.common.admin.service.MissionPromotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionPromotionRepositoryImpl implements MissionPromotionRepository {

    private final MissionPromotionJpaRepository regularMissionFactoryJpaRepository;

    @Override
    public List<MissionPromotion> findAll() {
        return regularMissionFactoryJpaRepository.findAll().stream()
                .map(MissionPromotionEntity::toModel)
                .toList();
    }

    @Override
    public MissionPromotion save(MissionPromotion adminCustomMission) {
        return regularMissionFactoryJpaRepository.save(
                MissionPromotionEntity.fromModel(adminCustomMission)
        ).toModel();
    }


}
