package com.example.demo.domain.mission;

public enum CustomMissionStateEnum {
    WAITING,    // 필터 대기 상태
    FILTERED,   // 승인 대기 상태(필터링은 되었지만 승인이 안된 경우)
    REJECTED    // 승인 거절 상태
}
