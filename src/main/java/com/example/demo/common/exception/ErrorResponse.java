package com.example.demo.common.exception;

import com.example.demo.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final Integer status;
    private final String errorCode;
    private final String message;

    ErrorResponse(Integer status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse from(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        return new ErrorResponse(code.getStatus().value(), code.getCode(), e.getClientMessage());
    }

    public static ErrorResponse internal() {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            "Unexpected error occurred. Please try again later.");
    }
}