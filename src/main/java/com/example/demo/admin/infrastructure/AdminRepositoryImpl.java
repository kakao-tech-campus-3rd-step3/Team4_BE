package com.example.demo.admin.infrastructure;

import com.example.demo.admin.domain.Admin;
import com.example.demo.admin.infrastructure.jpa.AdminEntity;
import com.example.demo.admin.infrastructure.jpa.AdminJpaRepository;
import com.example.demo.admin.service.AdminRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminJpaRepository adminJpaRepository;

    @Override
    public Optional<Admin> findByName(String name) {
        return adminJpaRepository.findByName(name).map(AdminEntity::toModel);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminJpaRepository.findById(id).map(AdminEntity::toModel);
    }
}
