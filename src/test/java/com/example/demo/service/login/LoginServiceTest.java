package com.example.demo.service.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.auth.controller.dto.TokenRequest;
import com.example.demo.auth.controller.dto.TokenResponse;
import com.example.demo.auth.infrastructure.jwt.JwtTokenProvider;
import com.example.demo.auth.service.AuthService;
import com.example.demo.exception.auth.AuthException;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class LoginServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private User testUser;
    private String validRefreshToken;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "TestUser");
        testUser = userRepository.save(testUser);

        validRefreshToken = jwtTokenProvider.createRefreshToken(testUser.getId());
        testUser.updateRefreshToken(validRefreshToken);
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("유효한 Refresh Token으로 토큰 재발급 성공")
    void reissue_success() throws InterruptedException {
        TokenRequest request = new TokenRequest(validRefreshToken);

        Thread.sleep(1000);

        TokenResponse response = authService.reissue(request);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getRefreshToken()).isNotBlank();
        assertThat(response.getRefreshToken()).isNotEqualTo(validRefreshToken);

        User updatedUser = userRepository.findById(testUser.getId()).orElseThrow();
        assertThat(updatedUser.getRefreshToken()).isEqualTo(response.getRefreshToken());

        assertThat(jwtTokenProvider.validateToken(response.getAccessToken())).isTrue();
        assertThat(jwtTokenProvider.validateToken(response.getRefreshToken())).isTrue();
        assertThat(jwtTokenProvider.getUserIdFromToken(response.getAccessToken())).isEqualTo(
            testUser.getId());
        assertThat(jwtTokenProvider.getUserIdFromToken(response.getRefreshToken())).isEqualTo(
            testUser.getId());
    }

    @Test
    @DisplayName("유효하지 않은 Refresh Token으로 재발급 시 실패")
    void reissue_fail_invalidToken() {
        String invalidToken = "invalid-token-string";
        TokenRequest request = new TokenRequest(invalidToken);

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("유효하지 않은 Refresh Token");
    }

    @Test
    @DisplayName("DB에 저장된 토큰과 다른 Refresh Token으로 재발급 시 실패")
    void reissue_fail_tokenMismatch() {
        String differentValidTokenForSameUser = jwtTokenProvider.createRefreshToken(
            testUser.getId());

        if (differentValidTokenForSameUser.equals(validRefreshToken)) {
            try {
                Thread.sleep(1000);
                differentValidTokenForSameUser = jwtTokenProvider.createRefreshToken(
                    testUser.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        assertThat(differentValidTokenForSameUser).isNotEqualTo(validRefreshToken);

        TokenRequest request = new TokenRequest(differentValidTokenForSameUser);

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("DB의 토큰과 일치하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 Refresh Token으로 재발급 시 실패")
    void reissue_fail_userNotFound() {
        Long nonExistentUserId = 9999L;
        String tokenForNonExistentUser = jwtTokenProvider.createRefreshToken(nonExistentUserId);
        TokenRequest request = new TokenRequest(tokenForNonExistentUser);

        assertThatThrownBy(() -> authService.reissue(request))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

}