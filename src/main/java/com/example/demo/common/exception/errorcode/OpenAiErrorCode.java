package com.example.demo.common.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum OpenAiErrorCode implements ErrorCode {
    OPEN_AI_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OPEN_AI_SERVER_ERROR",
            "외부 서버에 이상이 있어요. 나중에 다시 시도해주세요."),
    OPEN_AI_DATA_PARSE_ERROR(HttpStatus.BAD_REQUEST, "OPEN_AI_PARSE_ERROR",
            "AI 응답 데이터 형식이 올바르지 않아 처리할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    OpenAiErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
