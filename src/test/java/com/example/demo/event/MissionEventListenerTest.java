package com.example.demo.event;

import static org.mockito.Mockito.verify;

import com.example.demo.event.mission.MissionCompletionEvent;
import com.example.demo.event.mission.MissionEventListener;
import com.example.demo.event.mission.MissionExposureEvent;
import com.example.demo.event.mission.MissionSelectionEvent;
import com.example.demo.service.mission.MissionCounterService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MissionEventListenerTest {

    @Mock
    private MissionCounterService counterService;

    @InjectMocks
    private MissionEventListener listener;

    @Test
    void 추천_이벤트가_발생하면_CounterService의_addExposureDelta가_호출된다() {
        //when
        Long missionId = 1L;
        MissionExposureEvent event = new MissionExposureEvent(List.of(missionId));

        //then
        listener.handleMissionExposureEvent(event);

        //then
        verify(counterService).addExposureDelta(List.of(missionId));
    }

    @Test
    void 선택_이벤트가_발생하면_선택_횟수가_증가한다() {
        // given
        Long missionId = 1L;
        MissionSelectionEvent event = new MissionSelectionEvent(missionId);

        // when
        listener.handleMissionSelectionEvent(event);

        // then
        verify(counterService).addSelectionDelta(missionId);

    }

    @Test
    void 완료_이벤트가_발생하면_완료_횟수가_증가한다() {
        // given
        Long missionId = 1L;
        MissionCompletionEvent event = new MissionCompletionEvent(missionId);

        // when
        listener.handleMissionCompletionEvent(event);

        // then
        verify(counterService).addCompletionDelta(missionId);
    }

}
