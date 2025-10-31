package com.example.demo.service.cat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.cat.controller.dto.CatExistResponse;
import com.example.demo.cat.controller.dto.CatResponse;
import com.example.demo.cat.domain.Cat;
import com.example.demo.cat.service.CatRepository;
import com.example.demo.cat.service.CatService;
import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.CatErrorCode;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
public class CatServiceTest {

    @Autowired
    CatService catService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatRepository catRepository;

    User user;

    @BeforeEach
    void setUp() {
        User user = new User("abc@test.com", "상욱");
        this.user = userRepository.save(user);
    }

    @Nested
    class 고양이가_없는_경우 {
        @Test
        void 고양이를_생성할_수_있다() {
            //when
            String catName = "야옹이";
            Cat cat = catService.createCat(user, catName);

            //then
            Optional<Cat> getCat = catRepository.findById(cat.getUserId());
            assertThat(getCat).isPresent();
            cat = getCat.get();

            assertThat(cat.getName()).isEqualTo(catName);
            assertThat(cat.getUserId()).isEqualTo(user.getId());
        }

        @Test
        void 고양이_존재여부_조회_시_false를_반환한다() {
            CatExistResponse response = catService.checkCat(user);

            assertThat(response.isExist()).isFalse();
        }
    }


    @Nested
    class 고양이가_있는_경우 {

        @BeforeEach
        void setUpCat() {
            catService.createCat(user, "야옹이");
        }

        @Test
        void 이미_고양이가_있는_유저가_고양이_생성_시도_시_예외를_던진다() {
            //when
            String catName = "야옹이";
            assertThatThrownBy(() -> catService.createCat(user, catName))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(CatErrorCode.CAT_ALREADY_EXIST);
        }

        @Test
        void 고양이를_조회할_수_있다() {
            //when
            CatResponse catResponse = catService.getCat(user);

            //then
            assertThat(catResponse.getName()).isEqualTo("야옹이");
            assertThat(catResponse.getEquipped()).isNotNull();
            assertThat(catResponse.getEquipped().size()).isEqualTo(0);

        }

        @Test
        void 고양이_이름을_변경할_수_있다() {
            //when
            CatResponse catResponse = catService.rename(user, "고앵이");

            //then
            assertThat(catResponse.getName()).isEqualTo("고앵이");
        }

        @Test
        void 고양이가_아이템을_보유하고_있을_경우_아이템_목록도_같이_조회_할_수_있다() {
            // TODO
        }

        @Test
        void 고양이_존재여부_조회_시_true를_반환한다() {
            CatExistResponse response = catService.checkCat(user);

            assertThat(response.isExist()).isTrue();
        }
    }



}