package com.example.demo.exception;

public class CachingFailedException extends RuntimeException {

    private static final String MESSAGE = "미션 MIN/MAX를 가져오는데 실패했습니다.";

    public CachingFailedException() {
        super(MESSAGE);
    }

}
