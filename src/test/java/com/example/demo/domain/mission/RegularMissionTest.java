package com.example.demo.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.regular.domain.MissionCount;
import com.example.demo.mission.regular.domain.MissionScore;
import com.example.demo.mission.regular.domain.MissionTag;
import com.example.demo.mission.regular.domain.RegularMission;
import com.example.demo.plan.domain.MissionType;
import com.example.demo.plan.domain.Plan;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegularMissionTest {

    private MissionScore missionScore;
    private MissionCount missionCount;
    private List<MissionTag> tags;

    @BeforeEach
    void setUp() {
        // given: 테스트에 사용할 공통 객체 설정
        missionScore = new MissionScore(1, 2, 3, 4, 5, 6);
        missionCount = new MissionCount(10, 5, 3);
        tags = List.of(new MissionTag(1L, "건강"), new MissionTag(2L, "루틴"));
    }

    @Test
    @DisplayName("RegularMission 객체를 생성할 수 있다.")
    void 정규_미션_생성() {
        // when
        RegularMission mission = new RegularMission(
            1L,
            "아침 7시에 일어나기",
            MissionCategoryEnum.DAILY
        );

        // then
        assertThat(mission.getId()).isEqualTo(1L);
        assertThat(mission.getContent()).isEqualTo("아침 7시에 일어나기");
        assertThat(mission.getCategory()).isEqualTo(MissionCategoryEnum.DAILY);
    }

    @Test
    @DisplayName("미션 타입을 'REGULAR'로 올바르게 반환한다.")
    void 미션_타입_올바르게_반환() {
        // given
        RegularMission mission = new RegularMission(1L, "내용", MissionCategoryEnum.DAILY);

        // when
        MissionType missionType = mission.getMissionType();

        // then
        assertThat(missionType).isEqualTo(MissionType.REGULAR);
    }

    @Test
    @DisplayName("RegularMission을 Plan 객체로 변환할 수 있다.")
    void 미션을_Plan으로_변환() {
        // given
        Long userId = 100L;
        RegularMission mission = new RegularMission(
            1L,
            "운동하기",
            MissionCategoryEnum.REFRESH
        );

        // when
        Plan plan = mission.toPlan(userId);

        // then
        assertThat(plan.getId()).isNull(); // Plan ID는 저장 전이므로 null
        assertThat(plan.getUserId()).isEqualTo(userId);
        assertThat(plan.getMissionId()).isEqualTo(mission.getId());
        assertThat(plan.getContent()).isEqualTo(mission.getContent());
        assertThat(plan.getCategory()).isEqualTo(mission.getCategory());
        assertThat(plan.getMissionType()).isEqualTo(MissionType.REGULAR);
        assertThat(plan.isDone()).isFalse(); // Plan은 기본적으로 '미완료' 상태
    }
}