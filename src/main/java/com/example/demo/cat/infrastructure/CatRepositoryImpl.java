package com.example.demo.cat.infrastructure;

import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.infrastructure.jpa.CatEntity;
import com.example.demo.cat.infrastructure.jpa.CatJpaRepository;
import com.example.demo.cat.service.CatRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CatRepositoryImpl implements CatRepository {

    private final CatJpaRepository catJpaRepository;

    @Override
    public Cat save(Cat cat) {
        return catJpaRepository.save(CatEntity.fromModel(cat)).toModel();
    }

    @Override
    public Optional<Cat> findById(Long id) {
        return catJpaRepository.findById(id).map(CatEntity::toModel);
    }
}
