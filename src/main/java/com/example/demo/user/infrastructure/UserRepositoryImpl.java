package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.User;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import com.example.demo.user.infrastructure.jpa.UserJpaRepository;
import com.example.demo.user.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }
}
