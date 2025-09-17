package com.example.demo.event.mission;

import com.example.demo.service.mission.MissionCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MissionEventListener {

    private final MissionCounterService service;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionExposureEvent(MissionExposureEvent event) {
        service.addExposureDelta(event.getMissionIds());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionSelectionEvent(MissionSelectionEvent event) {
        service.addSelectionDelta(event.getMissionId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionCompletionEvent(MissionCompletionEvent event) {
        service.addCompletionDelta(event.getMissionId());
    }
}
