package com.example.demo.exception.business.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AdminErrorCode implements ErrorCode {

    ADMIN_ACCOUNT_NOT_EXIST(HttpStatus.UNAUTHORIZED, "ADMIN_ACCOUNT_NOT_EXIST", "관리자 계정이 존재하지 않습니다."),
    ADMIN_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "ADMIN_PASSWORD_NOT_MATCH", "비밀번호가 틀렸습니다."),

    INVALID_FILTER_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_FILTER_REQUEST", "WAITING 상태의 프로모션만 필터링할 수 있습니다."),
    INVALID_PROMOTE_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_PROMOTE_REQUEST", "FILTERED 상태의 미션만 승격할 수 있습니다."),
    PROMOTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "PROMOTION_NOT_FOUND", "미션 프로모션을 찾을 수 없습니다."),

    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    AdminErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
