package com.example.demo.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Users")
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

    @Builder
    public User(String name, String email, Integer point, String refreshToken) {
        this.name = name;
        this.email = email;
        this.point = point;
        this.refreshToken = refreshToken;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }
}
