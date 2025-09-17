package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.repository.MissionBatchUpdater;
import com.example.demo.service.mission.MissionCounterService;
import com.example.demo.service.mission.MissionDelta;
import com.example.demo.service.mission.MissionDelta.CounterType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MissionCounterServiceTest {

    @Mock
    private MissionBatchUpdater batchUpdater;

    @InjectMocks
    private MissionCounterService counterService;

    @Test
    void 추천_이벤트가_발생하면_노출_횟수가_증가한다() {
        //given
        Long missionId = 1L;

        //when
        counterService.addExposureDelta(List.of(missionId));

        //then
        Map<Long, MissionDelta> delta = counterService.getMissionDeltas().getDelta();
        assertThat(delta.get(missionId).getCount(CounterType.EXPOSURE)).isEqualTo(1);
    }

    @Test
    void 선택_이벤트가_발생하면_선택_횟수가_증가한다() {
        //given
        Long missionId = 1L;

        //when
        counterService.addSelectionDelta(missionId);

        //then
        Map<Long, MissionDelta> delta = counterService.getMissionDeltas().getDelta();
        assertThat(delta.get(missionId).getCount(CounterType.SELECTION)).isEqualTo(1);

    }

    @Test
    void 완료_이벤트가_발생하면_완료_횟수가_증가한다() {
        //given
        Long missionId = 1L;

        //when
        counterService.addCompletionDelta(missionId);

        //then
        Map<Long, MissionDelta> delta = counterService.getMissionDeltas().getDelta();
        assertThat(delta.get(missionId).getCount(CounterType.COMPLETION)).isEqualTo(1);
    }

}
