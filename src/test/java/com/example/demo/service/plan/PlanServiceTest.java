package com.example.demo.service.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.example.demo.config.MissionMinMaxCacheInitializer;
import com.example.demo.emotion.domain.Emotion;
import com.example.demo.emotion.domain.EmotionType;
import com.example.demo.emotion.service.EmotionRepository;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.PlanErrorCode;
import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.service.CustomMissionRepository;
import com.example.demo.mission.regular.infrastructure.jpa.MissionCountEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.MissionScoreEmbeddable;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionEntity;
import com.example.demo.mission.regular.infrastructure.jpa.RegularMissionJpaRepository;
import com.example.demo.mission.regular.service.ActivityService;
import com.example.demo.plan.controller.dto.PlanCreateRequest;
import com.example.demo.plan.controller.dto.PlanResponse;
import com.example.demo.plan.controller.dto.TodayPlansResponse;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import com.example.demo.plan.infrastructure.jpa.PlanEntity;
import com.example.demo.plan.infrastructure.jpa.PlanJpaRepository;
import com.example.demo.plan.service.PlanRepository;
import com.example.demo.plan.service.PlanService;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanJpaRepository planJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private RegularMissionJpaRepository regularMissionJpaRepository;

    @Autowired
    private CustomMissionRepository customMissionRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MissionMinMaxCacheInitializer cacheInitializer;

    private User testUser;
    private RegularMissionEntity testRegularMission;
    private CustomMission testCustomMission;
    private Emotion initialEmotion;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(new User("plan@test.com", "PlanUser"));
        initialEmotion = emotionRepository.save(
            new Emotion(testUser.getId(), 50, 50, 50, 50, 50, 50, 3.0, 3.0));

        testRegularMission = createTestRegularMission("정규 미션: 물 한 잔 마시기", MissionCategoryEnum.DAILY,
            1, 3, 1, 1, 3, 1, 1);
        testCustomMission = customMissionRepository.save(
            new CustomMission("커스텀 미션: 5분 명상", MissionCategoryEnum.REFRESH, testUser.getId()));

        entityManager.flush();
        cacheInitializer.initializeCache();
        entityManager.clear();
    }

    private RegularMissionEntity createTestRegularMission(
        String content, MissionCategoryEnum category, int sentiment, int energy, int cognitive,
        int relationship, int stress, int employment, int level) {

        MissionScoreEmbeddable scores = new MissionScoreEmbeddable(sentiment, energy, cognitive,
            relationship, stress, employment);
        MissionCountEmbeddable counts = new MissionCountEmbeddable();

        RegularMissionEntity mission = new RegularMissionEntity(null, content, category, level,
            scores, counts, new ArrayList<>());
        return regularMissionJpaRepository.save(mission);
    }

    private PlanCreateRequest createPlanRequest(Long missionId, MissionType type) {
        PlanCreateRequest request = new PlanCreateRequest();
        request.setMissionId(missionId);
        request.setMissionType(type);
        return request;
    }

    @Test
    @DisplayName("정규 미션을 오늘의 계획에 추가 성공")
    void addMissionToPlan_success_regular() {
        PlanCreateRequest request = createPlanRequest(testRegularMission.getId(),
            MissionType.REGULAR);

        activityService.addMissionToPlan(request, testUser);

        entityManager.flush();
        entityManager.clear();

        List<PlanEntity> todayPlans = planJpaRepository.findByUserIdAndCreatedAtBetween(
            testUser.getId(), LocalDate.now().atStartOfDay(),
            LocalDate.now().atTime(LocalTime.MAX));

        assertThat(todayPlans).hasSize(1);
        PlanEntity addedPlan = todayPlans.get(0);
        assertThat(addedPlan.getMissionId()).isEqualTo(testRegularMission.getId());
        assertThat(addedPlan.getMissionType()).isEqualTo(MissionType.REGULAR);
        assertThat(addedPlan.getContent()).isEqualTo(testRegularMission.getContent());
        assertThat(addedPlan.getCategory()).isEqualTo(testRegularMission.getCategory());
        assertThat(addedPlan.getIsDone()).isFalse();
        assertThat(addedPlan.getUserId()).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("커스텀 미션을 오늘의 계획에 추가 성공")
    void addMissionToPlan_success_custom() {
        PlanCreateRequest request = createPlanRequest(testCustomMission.getId(),
            MissionType.CUSTOM);

        activityService.addMissionToPlan(request, testUser);

        entityManager.flush();
        entityManager.clear();

        List<PlanEntity> todayPlans = planJpaRepository.findByUserIdAndCreatedAtBetween(
            testUser.getId(), LocalDate.now().atStartOfDay(),
            LocalDate.now().atTime(LocalTime.MAX));

        assertThat(todayPlans).hasSize(1);
        PlanEntity addedPlan = todayPlans.get(0);
        assertThat(addedPlan.getMissionId()).isEqualTo(testCustomMission.getId());
        assertThat(addedPlan.getMissionType()).isEqualTo(MissionType.CUSTOM);
        assertThat(addedPlan.getContent()).isEqualTo(testCustomMission.getContent());
        assertThat(addedPlan.getCategory()).isEqualTo(testCustomMission.getCategory());
        assertThat(addedPlan.getIsDone()).isFalse();
        assertThat(addedPlan.getUserId()).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("이미 추가된 미션을 다시 추가 시 실패")
    void addMissionToPlan_fail_alreadyExists() {
        PlanCreateRequest request = createPlanRequest(testRegularMission.getId(),
            MissionType.REGULAR);
        activityService.addMissionToPlan(request, testUser);
        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() -> activityService.addMissionToPlan(request, testUser))
            .isInstanceOf(BusinessException.class)
            .hasFieldOrPropertyWithValue("errorCode",
                PlanErrorCode.MISSION_ALREADY_EXIST_IN_TODAY_PLANS);
    }

    @Test
    @DisplayName("오늘의 계획 목록 조회 성공")
    void getTodayPlans_success() {
        PlanCreateRequest regularRequest = createPlanRequest(testRegularMission.getId(),
            MissionType.REGULAR);
        PlanCreateRequest customRequest = createPlanRequest(testCustomMission.getId(),
            MissionType.CUSTOM);
        activityService.addMissionToPlan(regularRequest, testUser);
        activityService.addMissionToPlan(customRequest, testUser);
        entityManager.flush();
        entityManager.clear();

        TodayPlansResponse response = planService.getTodayPlans(testUser);

        assertThat(response).isNotNull();
        assertThat(response.getPlans()).hasSize(2);
        assertThat(response.getPlans())
            .extracting(PlanResponse::getMissionId, PlanResponse::getMissionType)
            .containsExactlyInAnyOrder(
                tuple(testRegularMission.getId(), MissionType.REGULAR),
                tuple(testCustomMission.getId(), MissionType.CUSTOM)
            );
    }

    @Test
    @DisplayName("커스텀 미션 계획 완료 처리 성공 (감정 점수 변경 없음)")
    void updatePlanStatus_complete_custom() {
        PlanCreateRequest request = createPlanRequest(testCustomMission.getId(),
            MissionType.CUSTOM);
        activityService.addMissionToPlan(request, testUser);
        Plan addedPlan = planRepository.findTodayPlans(testUser).getPlans().get(0);
        Long planId = addedPlan.getId();

        activityService.updatePlanStatus(planId, true, testUser);

        entityManager.flush();
        entityManager.clear();

        Optional<PlanEntity> updatedPlanOpt = planJpaRepository.findById(planId);
        assertThat(updatedPlanOpt).isPresent();
        assertThat(updatedPlanOpt.get().getIsDone()).isTrue();

        Optional<Emotion> updatedEmotionOpt = emotionRepository.findById(testUser.getId());
        assertThat(updatedEmotionOpt).isPresent();
        Emotion updatedEmotion = updatedEmotionOpt.get();

        assertThat(updatedEmotion.getLevel(EmotionType.SENTIMENT)).isEqualTo(
            initialEmotion.getLevel(EmotionType.SENTIMENT));
        assertThat(updatedEmotion.getLevel(EmotionType.ENERGY)).isEqualTo(
            initialEmotion.getLevel(EmotionType.ENERGY));
        assertThat(updatedEmotion.getLevel(EmotionType.COGNITIVE)).isEqualTo(
            initialEmotion.getLevel(EmotionType.COGNITIVE));
        assertThat(updatedEmotion.getLevel(EmotionType.RELATIONSHIP)).isEqualTo(
            initialEmotion.getLevel(EmotionType.RELATIONSHIP));
        assertThat(updatedEmotion.getLevel(EmotionType.STRESS)).isEqualTo(
            initialEmotion.getLevel(EmotionType.STRESS));
        assertThat(updatedEmotion.getLevel(EmotionType.EMPLOYMENT)).isEqualTo(
            initialEmotion.getLevel(EmotionType.EMPLOYMENT));
    }

    @Test
    @DisplayName("완료된 계획을 미완료로 변경")
    void updatePlanStatus_uncomplete() {
        PlanCreateRequest request = createPlanRequest(testRegularMission.getId(),
            MissionType.REGULAR);
        activityService.addMissionToPlan(request, testUser);
        Plan addedPlan = planRepository.findTodayPlans(testUser).getPlans().get(0);
        Long planId = addedPlan.getId();
        activityService.updatePlanStatus(planId, true, testUser);
        entityManager.flush();
        entityManager.clear();

        activityService.updatePlanStatus(planId, false, testUser);

        entityManager.flush();
        entityManager.clear();

        Optional<PlanEntity> updatedPlanOpt = planJpaRepository.findById(planId);
        assertThat(updatedPlanOpt).isPresent();
        assertThat(updatedPlanOpt.get().getIsDone()).isFalse();
    }

    @Test
    @DisplayName("계획 삭제 성공")
    void deletePlan_success() {
        PlanCreateRequest request = createPlanRequest(testRegularMission.getId(),
            MissionType.REGULAR);
        activityService.addMissionToPlan(request, testUser);
        Plan addedPlan = planRepository.findTodayPlans(testUser).getPlans().get(0);
        Long planId = addedPlan.getId();
        entityManager.flush();
        entityManager.clear();

        planService.deletePlan(planId, testUser);

        entityManager.flush();
        entityManager.clear();

        Optional<PlanEntity> deletedPlanOpt = planJpaRepository.findById(planId);
        assertThat(deletedPlanOpt).isNotPresent();

        PlanCreateRequest customRequest = createPlanRequest(testCustomMission.getId(),
            MissionType.CUSTOM);
        activityService.addMissionToPlan(customRequest, testUser);
        Plan customPlan = planRepository.findTodayPlans(testUser).getPlans().get(0);
        entityManager.flush();
        entityManager.clear();

        planService.deletePlan(customPlan.getId(), testUser);
        entityManager.flush();
        entityManager.clear();

        TodayPlansResponse remainingPlans = planService.getTodayPlans(testUser);
        assertThat(remainingPlans.getPlans()).isEmpty();
    }

}
