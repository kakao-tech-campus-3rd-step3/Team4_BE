package com.example.demo.exception.service;

public class CachingFailedException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "미션 MIN/MAX 값을 가져오는데 실패했습니다.";

    public CachingFailedException() {
        super(DEFAULT_MESSAGE);
    }
}
