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
}
