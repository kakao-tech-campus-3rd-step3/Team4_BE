package com.example.demo.common.exception;

import com.example.demo.common.exception.errorcode.ErrorCode;

import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final RequestContext requestContext;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
        BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        String paramsStr = requestContext.getQueryParams().entrySet().stream()
            .map(m -> m.getKey() + ":" + Arrays.toString(m.getValue()))
            .collect(Collectors.joining(", "));

        if (errorCode.getStatus().is5xxServerError()) {
            log.error(
                "Internal server error occurred (errorCode={}): message={}, userId={}, requestUri={}, httpMethod={}, params={}",
                e.getErrorCode().getCode(),
                e.getClientMessage(),
                requestContext.getUserId(),
                requestContext.getRequestUri(),
                requestContext.getHttpMethod(),
                paramsStr,
                e);
        } else {
            log.warn(
                "Business exception occurred (errorCode={}): message={}, userId={}, requestUri={}, httpMethod={}, params={}",
                e.getErrorCode().getCode(),
                e.getClientMessage(),
                requestContext.getUserId(),
                requestContext.getRequestUri(),
                requestContext.getHttpMethod(),
                paramsStr
            );
        }

        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            messages.add(fieldError.getDefaultMessage());
        }
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
            "INVALID_REQUEST", messages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.internal());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(
        NoHandlerFoundException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "정의되지 않은 API 요청입니다.");
        body.put("path", e.getRequestURL());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}