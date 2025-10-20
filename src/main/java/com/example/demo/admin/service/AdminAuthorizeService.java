package com.example.demo.admin.service;

import com.example.demo.admin.domain.Admin;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.AdminErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAuthorizeService {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AdminRepository adminRepository;

    public Long login(String name, String rawPassword) {
        Admin admin = adminRepository.findByName(name)
            .orElseThrow(() -> new BusinessException(AdminErrorCode.ADMIN_ACCOUNT_NOT_EXIST));
        if (!encoder.matches(rawPassword, admin.getHashedPassword())) {
            throw new BusinessException(AdminErrorCode.ADMIN_PASSWORD_NOT_MATCH);
        }
        return admin.getId();
    }
}
