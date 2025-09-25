package com.example.demo.mission.custom.service;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.user.domain.User;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import com.example.demo.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomMissionService {

    private final CustomMissionRepository customMissionRepository;
    private final UserRepository userRepository;

    public CustomMission create(String content, MissionCategoryEnum category, User user) {
        CustomMission customMission = CustomMission.create(content, category, user.getId());
        UserEntity userEntity = UserEntity.fromModel(user);
        return customMissionRepository.save(customMission, userEntity);
    }

    public CustomMission update(Long missionId, String content, MissionCategoryEnum category,
        User user) {
        CustomMission mission = customMissionRepository.findById(missionId)
            .orElseThrow(() -> new RuntimeException("해당 미션을 찾을 수 없습니다. id: " + missionId));

        mission.validateUser(user.getId());
        mission.update(content, category);

        UserEntity userEntity = UserEntity.fromModel(user);
        return customMissionRepository.save(mission, userEntity);
    }

}
