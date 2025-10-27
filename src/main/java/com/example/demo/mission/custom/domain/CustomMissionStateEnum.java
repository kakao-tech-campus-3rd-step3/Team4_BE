package com.example.demo.mission.custom.domain;

public enum CustomMissionStateEnum {
    WAITING,    // 필터 대기 상태
    FILTERED,   // 승인 대기 상태(필터링은 되었지만 승인이 안된 경우) // ai 채점 완. 수정 가능
    PROMOTED,  // 승인 완료 상태
    REJECTED    // 승인 거절 상태
}