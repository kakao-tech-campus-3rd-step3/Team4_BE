package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum DiaryErrorCode implements ErrorCode {

    DIARY_NOT_FOUND(HttpStatus.BAD_REQUEST, "DIARY_NOT_FOUND", "일기를 찾을 수 없어요."),
    DIARY_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "DIARY_ACCESS_DENIED", "다른 사용자의 일기는 볼 수 없어요.");


    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    DiaryErrorCode(HttpStatus status, String code, String defaultMessage) {
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
