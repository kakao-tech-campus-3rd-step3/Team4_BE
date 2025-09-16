package com.example.demo.event.mission;

import com.example.demo.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MissionEventListener {

    private final MissionRepository missionRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionExposureEvent(MissionExposureEvent event) {
        missionRepository.incrementExposure(event.getMissionIds());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionSelectionEvent(MissionSelectionEvent event) {
        missionRepository.incrementSelection(event.getMissionId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionCompletionEvent(MissionCompletionEvent event) {
        missionRepository.incrementCompletion(event.getMissionId());
    }
}
