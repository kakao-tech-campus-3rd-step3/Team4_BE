package com.example.demo.user.domain;

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

    public void spendPoints(Integer point) {
        if (point > this.point) {
            throw new RuntimeException("포인트가 부족합니다.");
        }
        this.point -= point;
    }

    public void earnPoints(Integer point) {
        this.point += point;
    }
}
