package com.example.demo.common.admin.infrastructure;

import com.example.demo.common.admin.domain.MissionPromotion;
import com.example.demo.common.admin.infrastructure.jpa.MissionPromotionEntity;
import com.example.demo.common.admin.infrastructure.jpa.MissionPromotionJpaRepository;
import com.example.demo.common.admin.service.MissionPromotionRepository;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MissionPromotionRepositoryImpl implements MissionPromotionRepository {

    private final MissionPromotionJpaRepository missionPromotionJpaRepository;

    @Override
    public Optional<MissionPromotion> findById(Long id) {
        return missionPromotionJpaRepository.findById(id).map(MissionPromotionEntity::toModel);
    }

    @Override
    public Page<MissionPromotion> findAllByState(Pageable pageable, CustomMissionStateEnum state) {
        return missionPromotionJpaRepository.findAllByState(pageable, state)
            .map(MissionPromotionEntity::toModel);
    }

    @Override
    public MissionPromotion save(MissionPromotion adminCustomMission) {
        return missionPromotionJpaRepository.save(
                MissionPromotionEntity.fromModel(adminCustomMission)
        ).toModel();
    }


}
