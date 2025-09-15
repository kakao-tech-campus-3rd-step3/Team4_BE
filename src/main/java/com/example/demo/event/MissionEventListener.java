package com.example.demo.event;

import com.example.demo.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MissionEventListener {

    private final MissionRepository missionRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMissionCount(MissionCountEvent event) {
        switch (event.getType()) {
            case EXPOSURE -> missionRepository.incrementExposure(event.getMissionId());
            case SELECTION -> missionRepository.incrementSelection(event.getMissionId());
            case COMPLETION -> missionRepository.incrementCompletion(event.getMissionId());
        }
    }
}
