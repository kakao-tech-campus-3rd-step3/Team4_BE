package com.example.demo.exception.business.errorcode;

import org.springframework.http.HttpStatus;

public enum PlanErrorCode implements ErrorCode {

    PLAN_NOT_FOUND(HttpStatus.BAD_REQUEST, "PLAN_NOT_FOUND", "계획을 찾을 수 없어요."),
    MISSION_ALREADY_EXIST_IN_TODAY_PLANS(HttpStatus.BAD_REQUEST,
        "MISSION_ALREADY_EXIST_IN_TODAY_PLANS",
        "오늘의 계획에 이미 동일한 미션이 있어요."),
    CUSTOM_MISSION_CAN_BE_MODIFIED_ONLY(HttpStatus.BAD_REQUEST,
    "CUSTOM_MISSION_CAN_BE_MODIFIED_ONLY",
        "커스텀 미션만 수정할 수 있어요.");


    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    PlanErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }
}
