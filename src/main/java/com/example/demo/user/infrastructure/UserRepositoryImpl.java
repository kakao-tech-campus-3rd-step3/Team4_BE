package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.User;
import com.example.demo.user.infrastructure.jpa.UserEntity;
import com.example.demo.user.infrastructure.jpa.UserJpaRepository;
import com.example.demo.user.service.UserRepository;
import java.util.Optional;
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

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }
}
