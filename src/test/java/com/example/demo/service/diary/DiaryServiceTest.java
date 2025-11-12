package com.example.demo.service.diary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.diary.controller.dto.DiaryEmotionResponse;
import com.example.demo.diary.controller.dto.DiaryRequest;
import com.example.demo.diary.controller.dto.DiaryResponse;
import com.example.demo.diary.domain.Diary;
import com.example.demo.diary.domain.EmotionEnum;
import com.example.demo.diary.domain.Feedback;
import com.example.demo.diary.service.DiaryRepository;
import com.example.demo.emotion.service.EmotionService;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.DiaryErrorCode;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import jakarta.persistence.EntityManager;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class DiaryServiceTest {

    @Autowired
    FakeDiaryService fakeDiaryService;

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmotionService emotionService;

    @Autowired
    EntityManager em;

    private User user;

    @BeforeEach
    void setup() {
        // 테스트용 유저 생성
        user = userRepository.save(new User("test@test.com", "테스트유저"));
    }

    @Test
    void 일기_생성_성공() {
        // given
        DiaryRequest request = new DiaryRequest(EmotionEnum.GOOD, "오늘 하루는 즐거웠다");

        // when
        DiaryResponse response = fakeDiaryService.createDiary(request, user);

        // then
        Diary savedDiary = diaryRepository.findById(response.getId())
            .orElseThrow();
        assertThat(savedDiary.getId()).isNotNull();
        assertThat(savedDiary.getAuthor().getId()).isEqualTo(user.getId());
        assertThat(savedDiary.getEmotion()).isEqualTo(request.getEmotion());
        assertThat(savedDiary.getContent()).isEqualTo(request.getContent());
        assertThat(savedDiary.getFeedback().getContent()).isNotEmpty();
    }

    @Test
    void 존재하지_않는_일기_조회_시_예외() {
        // when
        assertThatThrownBy(() -> fakeDiaryService.getDiary(999L, user))
            .isInstanceOf(BusinessException.class)
            .satisfies(ex -> {
                BusinessException e = (BusinessException) ex;
                assertThat(e.getErrorCode()).isEqualTo(DiaryErrorCode.DIARY_NOT_FOUND);
            });
    }

    @Test
    void 다른_사용자의_일기_조회_시_접근_거부_예외() {
        // given
        User otherUser = userRepository.save(new User("other@test.com", "다른유저"));
        DiaryResponse response = fakeDiaryService.createDiary(
            new DiaryRequest(EmotionEnum.GOOD, "오늘 하루는 즐거웠다"), user);

        // when
        assertThatThrownBy(() -> fakeDiaryService.getDiary(response.getId(), otherUser))
            .isInstanceOf(BusinessException.class)
            .satisfies(ex -> {
                BusinessException e = (BusinessException) ex;
                assertThat(e.getErrorCode()).isEqualTo(DiaryErrorCode.DIARY_ACCESS_DENIED);
            });
    }

    @Test
    void 월별_일기_조회_성공() {
        // given
        YearMonth yearMonth = YearMonth.now();

        diaryRepository.save(
            new Diary(null, user, EmotionEnum.GOOD, "오늘 즐거웠다", new Feedback("feedback"), null));
        diaryRepository.save(
            new Diary(null, user, EmotionEnum.BAD, "조금 피곤했다", new Feedback("feedback"), null));

        em.flush();
        em.clear();

        // when
        List<DiaryEmotionResponse> monthlyDiaries = fakeDiaryService.getMonthlyDiaries(yearMonth, user);

        // then
        assertThat(monthlyDiaries).hasSize(2);
    }
}
