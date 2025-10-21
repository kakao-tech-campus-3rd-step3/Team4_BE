package com.example.demo.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.exception.business.BusinessException;
import com.example.demo.exception.business.errorcode.UserErrorCode;
import com.example.demo.domain.cat.CatTest.CatTestFixture;
import com.example.demo.user.domain.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void 유저를_생성할_수_있다() {
        //when
        User user = new User(1L, "test@test.com", "카테캠", 10000, "저는리프레시입니다");

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getName()).isEqualTo("카테캠");
        assertThat(user.getPoint()).isEqualTo(10000);
        assertThat(user.getRefreshToken()).isEqualTo("저는리프레시입니다");
    }

    @Test
    void 유저는_포인트를_얻을_수_있다() {
        //given
        User user = CatTestFixture.createUser();

        //when
        user.earnPoints(1000);

        //then
        assertThat(user.getPoint()).isEqualTo(11000);
    }

    @Test
    void 유저는_포인트를_사용할_수_있다() {
        //given
        User user = CatTestFixture.createUser();

        //when
        user.spendPoints(5000);

        //then
        assertThat(user.getPoint()).isEqualTo(5000);
    }

    @Test
    void 유저는_포인트를_사용할_때_잔액이_부족하면_예외를_던진다() {
        //given
        User user = CatTestFixture.createUser();

        //when
        assertThatThrownBy(
            () -> user.spendPoints(100000)
        ).satisfies(
            ex -> {
                BusinessException e = (BusinessException) ex;
                assertThat(e.getErrorCode()).isEqualTo(UserErrorCode.NOT_ENOUGH_POINTS);
            }
        ).isInstanceOf(BusinessException.class);
    }

    @Test
    void 유저는_이름을_변경할_수_있다() {
        //given
        User user = CatTestFixture.createUser();

        //when
        user.rename("상욱");

        //then
        assertThat(user.getName()).isEqualTo("상욱");
    }

    @Test
    void 유저는_리프레시_토큰을_갱신할_수_있다() {
        //given
        User user = CatTestFixture.createUser();

        //when
        user.updateRefreshToken("tokentoken");

        //then
        assertThat(user.getRefreshToken()).isEqualTo("tokentoken");
    }
}
