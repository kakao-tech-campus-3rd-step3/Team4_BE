package com.example.demo.cat.service;

import com.example.demo.cat.domain.Cat;
import java.util.Optional;

public interface CatRepository {

    Optional<Cat> findById(Long id);

    Cat save(Cat cat);
}
