package com.example.demo.common.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();
    String getCode();
    String getDefaultMessage();

}
