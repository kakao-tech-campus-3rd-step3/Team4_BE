package com.example.demo.exception.business.errorcode;

import org.springframework.http.HttpStatus;

public enum EmotionErrorCode implements ErrorCode {

    EMOTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "EMOTION_NOT_FOUND", "유저 감정을 찾을 수 없어요.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    EmotionErrorCode(HttpStatus status, String code, String defaultMessage) {
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
