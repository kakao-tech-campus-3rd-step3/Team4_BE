package com.example.demo.controller;

import com.example.demo.config.auth.CurrentUser;
import com.example.demo.domain.user.User;
import com.example.demo.dto.emotionTest.EmotionTestAnswersRequest;
import com.example.demo.dto.emotionTest.EmotionTestQuestionResponse;
import com.example.demo.service.EmotionTestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emotion-test")
@RequiredArgsConstructor
public class EmotionTestController {

    private final EmotionTestService emotionTestService;

    @GetMapping
    public ResponseEntity<List<EmotionTestQuestionResponse>> getAll() {
        return ResponseEntity.ok(emotionTestService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> applyResult(@RequestBody List<EmotionTestAnswersRequest> request,
        @CurrentUser User user) {
        emotionTestService.applyResult(user, request);
        return ResponseEntity.ok().build();
    }
}
