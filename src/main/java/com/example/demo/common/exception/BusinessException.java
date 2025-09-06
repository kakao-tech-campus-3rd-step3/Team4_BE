package com.example.demo.common.exception;

import com.example.demo.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String clientMessage;

    private BusinessException(ErrorCode errorCode, String clientMessage) {
        this.errorCode = errorCode;
        this.clientMessage = clientMessage;
    }

    private BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.clientMessage = errorCode.getDefaultMessage();
    }

    public static BusinessException of(ErrorCode errorCode, String clientMessage) {
        return new BusinessException(errorCode, clientMessage);
    }

    public static BusinessException from(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }
}
