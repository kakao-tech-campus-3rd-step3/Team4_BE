package com.example.demo.service;

import com.example.demo.domain.user.User;
import com.example.demo.domain.userEmotion.UserEmotion;
import com.example.demo.dto.mission.MissionResponse;
import com.example.demo.repository.MissionRepository;
import com.example.demo.repository.UserEmotionRepository;
import com.example.demo.repository.UserMissionRepository;
import jakarta.persistence.EntityManager;
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
    MissionRepository missionRepository;

    @Autowired
    UserMissionRepository userMissionRepository;

    @Autowired
    UserEmotionRepository userEmotionRepository;

    @Autowired
    MissionService missionService;

    @Autowired
    EntityManager em;

    User user;

    @BeforeEach
    void setUp() {
        user = User.of("김진욱", "testtest@naver.com");
        UserEmotion userEmotion = new UserEmotion(user, 0, 0, 0, 0, 0, 0);
        userEmotionRepository.save(userEmotion);
    }

    @Test
    void test() {
        List<MissionResponse> recommend = missionService.recommend(user);
        System.out.println(recommend);
    }
}
