package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum ItemErrorCode implements ErrorCode {

    ITEM_NOT_OWNED(HttpStatus.NOT_FOUND, "I001", "아이템을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    ItemErrorCode(HttpStatus status, String code, String defaultMessage) {
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
