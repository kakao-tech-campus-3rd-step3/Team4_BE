package com.example.demo.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();

    String getCode();

    String getDefaultMessage();

}