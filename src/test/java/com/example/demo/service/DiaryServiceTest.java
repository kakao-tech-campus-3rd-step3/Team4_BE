package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.common.exception.errorcode.DiaryErrorCode;
import com.example.demo.domain.diary.Diary;
import com.example.demo.domain.diary.EmotionEnum;
import com.example.demo.domain.user.User;
import com.example.demo.dto.diary.DiaryRequest;
import com.example.demo.dto.diary.DiaryResponse;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DiaryServiceTest {

    @Autowired
    DiaryService diaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    User user1;
    User user2;
    DiaryRequest request;
    Diary diary;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.of("홍길동", "user1@email.com"));
        user2 = userRepository.save(User.of("아무개", "user2@email.com"));

        request = new DiaryRequest(EmotionEnum.GOOD, "good");
        diary = new Diary(user2, request.getEmotion(), request.getContent());
        diaryRepository.save(diary);
    }

    @Test
    void 일기_생성_성공() {
        DiaryRequest createRequest = new DiaryRequest(EmotionEnum.GOOD, "baam~");
        Long id = diaryService.create(createRequest, user1).getId();

        em.flush();
        em.clear();

        Diary findDiary = diaryRepository.findById(id).orElseThrow();
        assertThat(findDiary.getAuthor().getId()).isEqualTo(user1.getId());
        assertThat(findDiary.getContent()).isEqualTo(createRequest.getContent());
    }

    @Test
    void 일기_조회_성공() {
        Long id = diary.getId();
        DiaryResponse diaryResponse = diaryService.get(id, user2);
        assertThat(diaryResponse.getContent()).isEqualTo(diary.getContent());
        assertThat(diaryResponse.getEmotion()).isEqualTo(diary.getEmotion());
    }

    @Test
    void 생성자_외_조회_시_실패() {
        assertThatThrownBy(
                () -> diaryService.get(diary.getId(), user1))
                .isInstanceOf(BusinessException.class)
                .satisfies(ex -> {
                    BusinessException e = (BusinessException) ex;
                    assertThat(e.getErrorCode()).isEqualTo(DiaryErrorCode.DIARY_ACCESS_DENIED);
                });
    }
}
