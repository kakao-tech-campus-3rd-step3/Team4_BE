package com.example.demo.exception.business.errorcode;

import org.springframework.http.HttpStatus;

public enum CatErrorCode implements ErrorCode {

    CAT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "CAT_ALREADY_EXIST", "이미 고양이 생성했어요."),
    CAT_NOT_FOUND(HttpStatus.BAD_REQUEST, "CAT_NOT_FOUND", "고양이를 찾을 수 없어요.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    CatErrorCode(HttpStatus status, String code, String defaultMessage) {
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
