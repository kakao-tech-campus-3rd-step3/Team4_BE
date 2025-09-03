package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private String refreshToken;

    protected User() {
    }

    private User(String email, String name, String refreshToken) {
        this.email = email;
        this.name = name;
        this.point = 0;
        this.refreshToken = refreshToken;
    }

    public static User of(String email, String name, String refreshToken) {
        return new User(email, name, refreshToken);
    }

    public void earnPoints(Integer point) {
        this.point += point;
    }

    public void spendPoints(Integer point) {
        this.point -= point;
    }
}