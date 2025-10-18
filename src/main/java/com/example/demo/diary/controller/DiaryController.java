package com.example.demo.diary.controller;

import com.example.demo.common.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.diary.service.DiaryService;
import com.example.demo.user.domain.User;
import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponse> create(@RequestBody DiaryRequest request, @CurrentUser User user) {
        DiaryResponse created = diaryService.createDiary(request, user);
        String location = "/api/diaries/" + created.getId();
        return ResponseEntity.created(URI.create(location)).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> view(@PathVariable Long id, @CurrentUser User user) {
        return ResponseEntity.ok(diaryService.getDiary(id, user));
    }

    @GetMapping
    public ResponseEntity<List<DiaryEmotionResponse>> getMonthlyDiaries(
        @RequestParam @DateTimeFormat(pattern = "yyyyMM") YearMonth month,
        @CurrentUser User user) {
        return ResponseEntity.ok(diaryService.getMonthlyDiaries(month, user));
    }
}
