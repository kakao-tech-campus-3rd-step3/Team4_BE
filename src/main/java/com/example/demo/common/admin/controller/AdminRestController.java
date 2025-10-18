package com.example.demo.common.admin.controller;

import com.example.demo.common.admin.controller.dto.AdminLoginRequest;
import com.example.demo.common.admin.service.AdminAuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminAuthorizeService authorizeService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AdminLoginRequest request, HttpServletRequest servletRequest) {
        Long id = authorizeService.login(request.getName(), request.getPassword());

        HttpSession oldSession = servletRequest.getSession(false);
        if (oldSession != null) oldSession.invalidate();
        HttpSession newSession = servletRequest.getSession(true);
        newSession.setAttribute("admin_id", id);
        newSession.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
