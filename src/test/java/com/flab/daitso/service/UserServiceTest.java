package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.exception.ExistingIdException;
import com.flab.daitso.exception.NotExistingIdException;
import com.flab.daitso.exception.WrongPasswordException;
import com.flab.daitso.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("정상적인 회원가입 테스트")
    public void 정상적인_회원가입_테스트() {
        UserRegister userRegister = new UserRegister("test1", "1q2w3e4r!", "test", "010-1111-2222");
        String userId = userService.join(userRegister);

        assertThat(userId).isEqualTo(userMapper.findByUserId("test1").getUserId());
    }

    @Test
    @DisplayName("중복 아이디 테스트")
    public void 중복_아이디_테스트() {
        UserRegister userRegister1 = new UserRegister("test1@naver.com", "1q2w3e4r!", "test", "010-1111-2222");
        UserRegister userRegister2 = new UserRegister("test1@naver.com", "1q2w3e4r!", "test", "010-1111-2222");
        userService.join(userRegister1);

        assertThrows(ExistingIdException.class, () -> userService.join(userRegister2));
    }

    @Test
    @DisplayName("로그인 테스트")
    public void 로그인_테스트() {
        String userId = "test1@naver.com";
        String userPassword = "1q2w3e4r!";

        UserRegister userRegister = new UserRegister(userId, userPassword, "test", "010-1111-2222");
        userService.join(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest(userId, userPassword);
        User login = userService.login(loginRequest);

        assertThat(login.getUserId()).isEqualTo(userId);
        assertThat(login.getUserId()).isEqualTo(userMapper.findByUserIdAndUserPassword(loginRequest).getUserId());
    }

    @Test
    @DisplayName("존재하지 않는 아이디 테스트")
    public void 로그인_실패_테스트1() {
        String userId = "test1@naver.com";
        String userPassword = "1q2w3e4r!";

        UserRegister userRegister = new UserRegister(userId, userPassword, "test", "010-1111-2222");
        userService.join(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest("testest", userPassword);

        assertThrows(NotExistingIdException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("비밀번호 오류 테스트")
    public void 로그인_실패_테스트2() {
        String userId = "test1@naver.com";
        String userPassword = "1q2w3e4r!";

        UserRegister userRegister = new UserRegister(userId, userPassword, "test", "010-1111-2222");
        userService.join(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest("test1@naver.com", "11111");

        assertThrows(WrongPasswordException.class, () -> userService.login(loginRequest));
    }
}
