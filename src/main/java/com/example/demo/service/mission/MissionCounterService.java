package com.example.demo.service.mission;

import static com.example.demo.service.mission.MissionDelta.CounterType.COMPLETION;
import static com.example.demo.service.mission.MissionDelta.CounterType.EXPOSURE;
import static com.example.demo.service.mission.MissionDelta.CounterType.SELECTION;

import com.example.demo.repository.MissionBatchUpdater;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionCounterService {

    @Getter
    private final MissionDeltas missionDeltas = new MissionDeltas();
    private final MissionBatchUpdater batchUpdater;

    public void addExposureDelta(List<Long> missionIds) {
        missionDeltas.increment(missionIds, EXPOSURE);
    }

    public void addSelectionDelta(Long missionId) {
        missionDeltas.increment(missionId, SELECTION);
    }

    public void addCompletionDelta(Long missionId) {
        missionDeltas.increment(missionId, COMPLETION);
    }

    @Transactional
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5분
    public void flushDeltas() {
        Map<Long, MissionDelta> deltas = missionDeltas.getDelta();
        if (deltas.isEmpty()) return;

        Map<Long, MissionDelta> snapshot = new HashMap<>(deltas);
        deltas.clear();

        batchUpdater.batchUpdateCounter(snapshot);
    }
}
