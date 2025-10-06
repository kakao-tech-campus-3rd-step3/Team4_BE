package com.example.demo.common.auth.service;

import com.example.demo.common.auth.controller.dto.TokenRequest;
import com.example.demo.common.auth.controller.dto.TokenResponse;
import com.example.demo.common.auth.infrastructure.jwt.JwtTokenProvider;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequestDto) {

        String refreshToken = tokenRequestDto.getRefreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 Refresh Token 입니다.");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("DB의 토큰과 일치하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(userId);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        user.updateRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
