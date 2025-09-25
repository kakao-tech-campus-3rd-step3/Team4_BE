package com.example.demo.user.infrastructure.jpa;

import com.example.demo.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer point;

    private String refreshToken;

    private UserEntity(Long id, String name, String email, Integer point, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.point = point;
        this.refreshToken = refreshToken;
    }

    private UserEntity(Long id) {
        this.id = id;
    }

    public User toModel() {
        return new User(id, email, name, point, refreshToken);
    }

    public static UserEntity fromModel(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getPoint(),
            user.getRefreshToken());
    }

    public static UserEntity fromId(Long userId) {
        return new UserEntity(userId);
    }
}