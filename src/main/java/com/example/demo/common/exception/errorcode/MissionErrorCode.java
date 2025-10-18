package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum MissionErrorCode implements ErrorCode {

    MISSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MISSION_NOT_FOUND", "미션을 찾을 수 없어요."),
    NORMALIZATION_BASELINE_MISSING(HttpStatus.INTERNAL_SERVER_ERROR,
            "NORMALIZATION_BASELINE_MISSING",
            "정규화 MIN/MAX 계산에 필요한 미션 데이터가 없어요.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    MissionErrorCode(HttpStatus status, String code, String defaultMessage) {
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
