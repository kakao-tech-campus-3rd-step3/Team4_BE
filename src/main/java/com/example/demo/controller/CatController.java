package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.user.User;
import com.example.demo.dto.cat.CatNameRequest;
import com.example.demo.dto.cat.CatResponse;
import com.example.demo.service.CatService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;

    @PostMapping
    public ResponseEntity<Void> createCat(@RequestBody CatNameRequest request,
        @CurrentUser User user) {
        CatResponse created = catService.create(user, request.getName());
        String location = "/api/cats";
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping
    public ResponseEntity<CatResponse> getCat(@CurrentUser User user) {
        CatResponse found = catService.get(user);
        return ResponseEntity.ok(found);
    }

    @PutMapping
    public ResponseEntity<CatResponse> renameCat(@RequestBody CatNameRequest request,
        @CurrentUser User user) {
        CatResponse updated = catService.rename(user, request.getName());
        return ResponseEntity.ok(updated);
    }
}
