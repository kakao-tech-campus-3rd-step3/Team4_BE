package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    NOT_ENOUGH_POINTS(HttpStatus.BAD_REQUEST, "NOT_ENOUGH_POINTS", "포인트가 부족해요.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    UserErrorCode(HttpStatus status, String code, String defaultMessage) {
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
