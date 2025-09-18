package com.example.demo.controller;

import com.example.demo.dto.auth.TokenRequest;
import com.example.demo.dto.auth.TokenResponse;
import com.example.demo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponseDto = authService.reissue(tokenRequest);
        return ResponseEntity.ok(tokenResponseDto);
    }
}