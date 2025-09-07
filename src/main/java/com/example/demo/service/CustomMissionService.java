package com.example.demo.service;

import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.mission.CustomMissionStateEnum;
import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.dto.mission.CustomMissionRequest;
import com.example.demo.repository.CustomMissionRepository;
import com.example.demo.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomMissionService {

    private final CustomMissionRepository customMissionRepository;
    private final UserMissionRepository userMissionRepository;

    public CustomMission create(CustomMissionRequest request, User user) {
        CustomMission customMission = CustomMission.of(
                user,
                request.getContent(),
                request.getCategory(),
                CustomMissionStateEnum.WAITING
        );
        CustomMission savedCustomMission = customMissionRepository.save(customMission);

        UserMission userMission = UserMission.of(
                user,
                savedCustomMission
        );
        userMissionRepository.save(userMission);
        return savedCustomMission;
    }

    @Transactional
    public CustomMission update(Long id, CustomMissionRequest request, User user) {
        CustomMission customMission = customMissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("미션을 찾는데 실패하였습니다."));

        if (!customMission.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 미션만 수정할 수 있습니다.");
        }

        customMission.update(request.getContent(), request.getCategory());
        customMissionRepository.save(customMission);

        return customMission;
    }
}
