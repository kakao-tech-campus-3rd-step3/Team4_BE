package com.example.demo.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum ItemErrorCode implements ErrorCode {

    ITEM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ITEM_ALREADY_EXIST", "이미 아이템을 소유하고 있어요."),
    ITEM_NOT_EXIST(HttpStatus.BAD_REQUEST, "ITEM_NOT_EXIST", "아이템을 소유하고 있지 않아요."),

    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다.");

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
