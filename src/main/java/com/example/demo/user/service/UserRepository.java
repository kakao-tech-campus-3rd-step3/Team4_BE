package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}
