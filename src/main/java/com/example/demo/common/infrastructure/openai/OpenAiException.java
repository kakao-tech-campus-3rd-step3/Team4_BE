package com.example.demo.common.infrastructure.openai;

public class OpenAiException extends RuntimeException {

    public OpenAiException(String message) {
        super(message);
    }
}
