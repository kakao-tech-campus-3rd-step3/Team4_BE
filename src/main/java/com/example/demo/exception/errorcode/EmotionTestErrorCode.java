package com.example.demo.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EmotionTestErrorCode implements ErrorCode {
    ALREADY_TESTED(HttpStatus.BAD_REQUEST, "ALREADY_TESTED", "이미 테스트를 완료한 계정이예요."),
    UNFINISHED_TEST_RESULT(HttpStatus.BAD_REQUEST, "UNFINISHED_TEST_RESULT", "테스트 결과가 누락되었어요. 개발자에게 문의하세요."),
    UNDEFINED_QUESTION_ID(HttpStatus.BAD_REQUEST, "UNDEFINED_QUESTION_ID", "테스트 결과에 범위를 초과한 값이 있어요. 개발자에게 문의하세요."),
    ILLEGAL_CHOICE_INDEX(HttpStatus.BAD_REQUEST, "ILLEGAL_CHOICE_INDEX", "테스트 결과에 범위를 초과한 값이 있어요. 개발자에게 문의하세요.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    EmotionTestErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status = status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
