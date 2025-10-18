package com.example.demo.common.admin.service;

import com.example.demo.common.admin.domain.Admin;
import java.util.Optional;

public interface AdminRepository {
    Optional<Admin> findByName(String name);
    Optional<Admin> findById(Long id);
}
