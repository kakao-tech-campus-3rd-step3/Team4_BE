package com.example.demo.common.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EmotionTestErrorCode implements ErrorCode {
    ALREADY_TESTED(HttpStatus.BAD_REQUEST, "ALREADY_TESTED", ""),
    UNFINISHED_TEST_RESULT(HttpStatus.BAD_REQUEST, "UNFINISHED_TEST_RESULT", ""),
    UNDEFINED_QUESTION_ID(HttpStatus.BAD_REQUEST, "UNDEFINED_QUESTION_ID", ""),
    ILLEGAL_CHOICE_INDEX(HttpStatus.BAD_REQUEST, "ILLEGAL_CHOICE_INDEX", "");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    EmotionTestErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
