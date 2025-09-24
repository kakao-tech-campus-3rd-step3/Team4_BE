package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.user.User;
import com.example.demo.dto.diary.CreateDiaryResponse;
import com.example.demo.dto.diary.DiaryRequest;
import com.example.demo.dto.diary.DiaryResponse;
import com.example.demo.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<CreateDiaryResponse> create(@RequestBody DiaryRequest request,
            @CurrentUser User user) {
        CreateDiaryResponse createDiaryResponse = diaryService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createDiaryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> get(@PathVariable Long id, @CurrentUser User user) {
        DiaryResponse response = diaryService.get(id, user);
        return ResponseEntity.ok(response);
    }
}
