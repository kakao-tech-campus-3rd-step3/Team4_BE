package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.domain.cat.Cat;
import com.example.demo.domain.user.User;
import com.example.demo.dto.cat.CatResponse;
import com.example.demo.repository.CatRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CatServiceTest {

    @Autowired
    CatService catService;

    @Autowired
    CatRepository catRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    User user;
    User readyUser;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.of("김김김", "kimkimkim@test.com"));
        readyUser = userRepository.save(User.of("김완벽", "perfect@test.com"));

        catService.create(readyUser, "완벽한 고양이");
    }

    @Test
    void 고양이_생성_성공() {
        catService.create(user, "testCat");

        em.flush();
        em.clear();

        Optional<Cat> cat = catRepository.findById(user.getId());
        assertThat(cat.isPresent()).isTrue();
        assertThat(cat.get().getName()).isEqualTo("testCat");
    }

    @Test
    void 생성시_이미_고양이가_존재하면_예외() {
        assertThrows(RuntimeException.class, () -> {
            catService.create(readyUser, "testCat");
        });
    }

    @Test
    void 고양이_조회_성공() {
        CatResponse cat = catService.get(readyUser);

        assertThat(cat.getName()).isEqualTo("완벽한 고양이");
    }

    @Test
    void 조회시_고양이가_존재하지_않으면_예외() {
        assertThrows(RuntimeException.class, () -> {
            catService.get(user);
        });
    }

    @Test
    void 고양이_수정_성공() {
        catService.rename(readyUser, "새로운 이름");

        em.flush();
        em.clear();

        assertThat(catRepository.findById(readyUser.getId()).get().getName()).isEqualTo("새로운 이름");
    }

    @Test
    void 수정시_고양이가_존재하지_않으면_예외() {
        assertThrows(RuntimeException.class, () -> {
            catService.rename(user, "새로운 이름");
        });
    }
}
