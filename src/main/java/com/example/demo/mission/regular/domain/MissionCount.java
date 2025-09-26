package com.example.demo.mission.regular.domain;

import lombok.Getter;

@Getter
public class MissionCount {

    private Integer exposureCount;
    private Integer selectionCount;
    private Integer completionCount;

    public MissionCount(Integer exposureCount, Integer selectionCount, Integer completionCount) {
        this.exposureCount = exposureCount;
        this.selectionCount = selectionCount;
        this.completionCount = completionCount;
    }
}
