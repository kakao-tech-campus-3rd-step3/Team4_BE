package com.example.demo.common.auth.controller;

import com.example.demo.common.auth.controller.dto.TokenRequest;
import com.example.demo.common.auth.controller.dto.TokenResponse;
import com.example.demo.common.auth.service.AuthService;
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
        TokenResponse tokenResponse = authService.reissue(tokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}