package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.domain.mission.CustomMission;
import com.example.demo.domain.mission.MissionCategoryEnum;
import com.example.demo.domain.mission.UserMission;
import com.example.demo.domain.user.User;
import com.example.demo.dto.mission.CustomMissionRequest;
import com.example.demo.repository.CustomMissionRepository;
import com.example.demo.repository.UserMissionRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CustomMissionServiceTest {

    @Autowired
    CustomMissionService customMissionService;

    @Autowired
    CustomMissionRepository customMissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMissionRepository userMissionRepository;

    @Autowired
    EntityManager em;

    User user1;
    User user2;
    CustomMissionRequest createRequest;
    CustomMission createdMission;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.of("홍길동", "email@email.com"));
        user2 = userRepository.save(User.of("아무개", "email@email.com"));
        createRequest = new CustomMissionRequest("content", MissionCategoryEnum.DAILY);
        createdMission = customMissionService.create(createRequest, user1);
    }

    @Test
    void 커스텀_미션_생성_성공() {
        // Then
        assertThat(createdMission.getContent()).isEqualTo("content");
        assertThat(createdMission.getAuthor()).isEqualTo(user1);

        em.flush();
        em.clear();

        List<UserMission> userMissions = userMissionRepository.findByUser(user1);
        UserMission userMission = userMissions.get(0);
        assertThat(userMission.getUser().getName()).isEqualTo("홍길동");
        assertThat(userMission.getCustomMission().getId()).isEqualTo(createdMission.getId());
        assertThat(userMission.getCreatedAt()).isNotNull();
    }

    @Test
    void 미션_수정_성공() {
        // Given
        createRequest = new CustomMissionRequest("update content", MissionCategoryEnum.DAILY);

        // When
        customMissionService.update(createdMission.getId(), createRequest, user1);

        // Then
        em.flush();
        em.clear();

        CustomMission updatedMission = customMissionRepository.findById(createdMission.getId())
                .get();
        assertThat(updatedMission.getContent()).isEqualTo("update content");
    }

    @Test
    void 소유자_외_수정_시도_시_예외_발생() {
        // Given
        CustomMissionRequest updateRequest = new CustomMissionRequest("new content",
                MissionCategoryEnum.DAILY);

        // When & Then
        assertThatThrownBy(
                () -> customMissionService.update(createdMission.getId(), updateRequest, user2))
                .isInstanceOf(RuntimeException.class);
    }
}
