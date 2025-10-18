package com.example.demo.auth.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {

    private String refreshToken;
}