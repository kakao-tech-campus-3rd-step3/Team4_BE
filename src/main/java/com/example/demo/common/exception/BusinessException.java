package com.example.demo.common.exception;

import com.example.demo.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String clientMessage;

    public BusinessException(ErrorCode errorCode, String clientMessage) {
        this.errorCode = errorCode;
        this.clientMessage = clientMessage;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.clientMessage = errorCode.getDefaultMessage();
    }
}