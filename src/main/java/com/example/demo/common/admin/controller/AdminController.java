package com.example.demo.common.admin.controller;

import com.example.demo.common.admin.controller.dto.CreateProductItemRequest;
import com.example.demo.common.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admin/items")
    public ResponseEntity<Void> registerItem(@RequestBody CreateProductItemRequest request) {
        adminService.registerItem(request);
        return ResponseEntity.ok().build();
    }

}
