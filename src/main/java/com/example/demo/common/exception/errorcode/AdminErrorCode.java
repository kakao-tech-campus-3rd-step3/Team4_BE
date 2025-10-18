package com.example.demo.common.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AdminErrorCode implements ErrorCode {

    ADMIN_ACCOUNT_NOT_EXIST(HttpStatus.UNAUTHORIZED, "ADMIN_ACCOUNT_NOT_EXIST", "관리자 계정이 존재하지 않습니다."),
    ADMIN_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "ADMIN_PASSWORD_NOT_MATCH", "비밀번호가 틀렸습니다.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    AdminErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
