package com.example.demo.domain.mission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.mission.MissionCategoryEnum;
import com.example.demo.mission.custom.domain.CustomMission;
import com.example.demo.mission.custom.domain.CustomMissionStateEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomMissionTest {

    @Test
    @DisplayName("정적 팩토리 메서드로 CustomMission을 생성할 수 있다.")
    void 미션_생성() {
        // given
        Long authorId = 1L;
        String content = "매일 아침 책 10페이지 읽기";
        MissionCategoryEnum category = MissionCategoryEnum.DAILY;

        // when
        CustomMission mission = CustomMission.create(content, category, authorId);

        // then
        assertThat(mission.getContent()).isEqualTo(content);
        assertThat(mission.getCategory()).isEqualTo(category);
        assertThat(mission.getUserId()).isEqualTo(authorId);
        assertThat(mission.getState()).isEqualTo(CustomMissionStateEnum.WAITING);
        assertThat(mission.getId()).isNull(); // ID는 저장 전이므로 null
    }

    @Test
    @DisplayName("미션의 내용과 카테고리를 수정할 수 있다.")
    void 미션_수정() {
        // given
        CustomMission mission = CustomMission.create("기존 내용", MissionCategoryEnum.DAILY, 1L);
        String newContent = "새로운 내용으로 수정";
        MissionCategoryEnum newCategory = MissionCategoryEnum.EMPLOYMENT;

        // when
        mission.update(newContent, newCategory);

        // then
        assertThat(mission.getContent()).isEqualTo(newContent);
        assertThat(mission.getCategory()).isEqualTo(newCategory);
    }

    @Test
    @DisplayName("미션의 작성자는 검증을 통과한다.")
    void 미션_작성자_검증_성공() {
        // given
        Long authorId = 1L;
        CustomMission mission = CustomMission.create("내용", MissionCategoryEnum.REFRESH, authorId);

        // when & then
        // 예외가 발생하지 않아야 함
        mission.validateUser(authorId);
    }

    @Test
    @DisplayName("미션의 작성자가 아니면 예외를 발생시킨다.")
    void 미션_작성자_검증_실패() {
        // given
        Long authorId = 1L;
        Long otherUserId = 2L;
        CustomMission mission = CustomMission.create("내용", MissionCategoryEnum.REFRESH, authorId);

        // when & then
        assertThatThrownBy(() -> mission.validateUser(otherUserId))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("미션을 수정하거나 삭제할 권한이 없습니다.");
    }
}