package com.example.demo.exception.service;

public class OpenAiException extends ServiceException {

    public OpenAiException(Throwable cause) {
        super(cause);
    }

    public OpenAiException(String message) {
        super(message);
    }

    public OpenAiException(String message, Throwable cause) {
        super(message, cause);
    }
}
