package com.example.demo.cat.controller;

import com.example.demo.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.cat.controller.dto.CatNameRequest;
import com.example.demo.cat.controller.dto.CatResponse;
import com.example.demo.cat.service.CatService;
import com.example.demo.user.domain.User;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createCat(@RequestBody @Valid CatNameRequest request,
        @CurrentUser User user) {
        catService.createCat(user, request.getName());
        String location = "/api/cats";
        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping
    public ResponseEntity<CatResponse> getCat(@CurrentUser User user) {
        CatResponse found = catService.getCat(user);
        return ResponseEntity.ok(found);
    }

    @PutMapping
    public ResponseEntity<CatResponse> renameCat(@RequestBody @Valid CatNameRequest request,
        @CurrentUser User user) {
        CatResponse updated = catService.rename(user, request.getName());
        return ResponseEntity.ok(updated);
    }
}
