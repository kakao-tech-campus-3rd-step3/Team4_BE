package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum EmotionErrorCode implements ErrorCode {

    EMOTION_ERROR_CODE(HttpStatus.BAD_REQUEST, "EMOTION_ERROR_CODE", "유저감정을 찾을 수 없어요.");

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
