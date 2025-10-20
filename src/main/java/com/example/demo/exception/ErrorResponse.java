package com.example.demo.exception;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse {

    private final Integer status;
    private final String errorCode;
    private final List<String> message;

    ErrorResponse(Integer status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = List.of(message);
    }

    ErrorResponse(Integer status, String errorCode, List<String> message) {
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
            "잠시 문제가 생겼어요. 조금 뒤 다시 시도해주세요!");
    }
}