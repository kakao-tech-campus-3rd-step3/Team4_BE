package com.example.demo.user.domain;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.UserErrorCode;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String email;
    private String name;
    private Integer point;
    private String refreshToken;

    public User(Long id, String email, String name, Integer point, String refreshToken) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.point = point;
        this.refreshToken = refreshToken;
    }

    protected User() {
    }

    public User(String email, String name) {
        this(null, email, name, 0, null);
    }

    public void rename(String name) {
        this.name = name;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void spendPoints(Integer point) {
        if (point > this.point) {
            throw new BusinessException(UserErrorCode.NOT_ENOUGH_POINTS);
        }
        this.point -= point;
    }

    public void earnPoints(Integer point) {
        this.point += point;
    }
}
