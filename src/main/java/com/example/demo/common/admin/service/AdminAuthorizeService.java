package com.example.demo.common.admin.service;

import com.example.demo.common.admin.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAuthorizeService {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final AdminRepository adminRepository;

//    @PostConstruct
//    public void check() {
//        Admin admin = adminRepository.findByName("admin")
//            .orElseThrow(() -> new RuntimeException("admin 없음"));
//        if (!encoder.matches("rkddnjseo4xla123!@#", admin.getHashedPassword())) {
//            throw new RuntimeException("초기화 실패");
//        }
//    }

    public Long login(String name, String rawPassword) {
        Admin admin = adminRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("admin 없음"));
        if (!encoder.matches(rawPassword, admin.getHashedPassword())) {
            throw new RuntimeException("로그인 실패: " + name + " " + rawPassword + " " + admin.getHashedPassword());
        }
        return admin.getId();
    }
}
