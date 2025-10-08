package com.example.demo.mission.regular.infrastructure.jpa;

import com.example.demo.mission.regular.domain.MissionCount;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MissionCountEmbeddable {

    @Column(nullable = false)
    private Integer exposureCount;

    @Column(nullable = false)
    private Integer selectionCount;

    @Column(nullable = false)
    private Integer completionCount;

    public MissionCountEmbeddable() {
        this.exposureCount = 0;
        this.selectionCount = 0;
        this.completionCount = 0;
    }

    private MissionCountEmbeddable(Integer exposureCount, Integer selectionCount, Integer completionCount) {
        this.exposureCount = exposureCount;
        this.selectionCount = selectionCount;
        this.completionCount = completionCount;
    }

    public MissionCount toModel() {
        return new MissionCount(exposureCount, selectionCount, completionCount);
    }

    public static MissionCountEmbeddable fromModel(MissionCount missionCount) {
        return new MissionCountEmbeddable(missionCount.getExposureCount(), missionCount.getSelectionCount(), missionCount.getCompletionCount());
    }
}
