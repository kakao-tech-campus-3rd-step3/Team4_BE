package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.mission.Mission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.dto.mission.MissionResponse;
import com.example.demo.repository.MissionRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MissionServiceTest {

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    private Mission mission1;
    private Mission mission2;

    @BeforeEach
    void setUp() {
        mission1 = new Mission("산책하기", MissionCategoryEnum.DAILY, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        mission2 = new Mission("이력서 쓰기", MissionCategoryEnum.EMPLOYMENT, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        missionRepository.saveAll(List.of(mission1, mission2));
    }

    @Test
    void 추천_미션_목록_조회_성공() {
        List<MissionResponse> recommendedMissions = missionService.getRecommendedMissions();

        assertThat(recommendedMissions).isNotNull();
        assertThat(recommendedMissions.size()).isEqualTo(2);

        MissionResponse firstMission = recommendedMissions.get(0);
        assertThat(firstMission.getId()).isEqualTo(mission1.getId());
        assertThat(firstMission.getContent()).isEqualTo("산책하기");
        assertThat(firstMission.getCategory()).isEqualTo(MissionCategoryEnum.DAILY);
    }
}