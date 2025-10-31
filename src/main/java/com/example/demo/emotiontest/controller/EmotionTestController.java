package com.example.demo.emotiontest.controller;

import com.example.demo.auth.infrastructure.resolver.CurrentUser;
import com.example.demo.emotiontest.controller.dto.EmotionTestRequest;
import com.example.demo.emotiontest.controller.dto.EmotionTestResponse;
import com.example.demo.emotiontest.service.EmotionTestService;
import com.example.demo.user.domain.User;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<EmotionTestResponse>> viewAll() {
        return ResponseEntity.ok(emotionTestService.viewAll());
    }

    @PostMapping
    public ResponseEntity<Void> applyResult(@RequestBody @Valid EmotionTestRequest request,
        @CurrentUser User user) {
        emotionTestService.applyEmotionTestResult(user, request.getRequest());
        return ResponseEntity.ok().build();
    }

}
