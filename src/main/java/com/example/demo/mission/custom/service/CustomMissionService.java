package com.example.demo.mission.custom.service;

import com.example.demo.admin.domain.MissionPromotion;
import com.example.demo.admin.service.MissionPromotionRepository;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomMissionService {

    private final CustomMissionRepository customMissionRepository;
    private final MissionPromotionRepository missionPromotionRepository;

    public CustomMission create(String content, MissionCategoryEnum category, User user) {
        CustomMission customMission = new CustomMission(content, category, user.getId());
        MissionPromotion adminCustomMission = new MissionPromotion(content, category);
        missionPromotionRepository.save(adminCustomMission);
        return customMissionRepository.save(customMission);
    }

    public CustomMission update(Long missionId, String content, MissionCategoryEnum category,
            User user) {
        CustomMission mission = customMissionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("해당 미션을 찾을 수 없습니다. id: " + missionId));

        mission.validateUser(user.getId());
        mission.update(content, category);

        return customMissionRepository.save(mission);
    }

}
