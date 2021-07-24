package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.error.exception.user.DifferentPasswordException;
import com.flab.daitso.error.exception.user.ExistingIdException;
import com.flab.daitso.error.exception.user.NotExistingIdException;
import com.flab.daitso.error.exception.user.WrongPasswordException;
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

    private static String userEmail = "test1@naver.com";
    private static String userPassword = "1q2w3e4r!";
    private static String confirmUserPassword = "1q2w3e4r!";
    private static String name = "park";
    private static String phoneNumber = "010-1111-2222";

    private static UserRegister userRegister = new UserRegister(userEmail, userPassword, confirmUserPassword, name, phoneNumber);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 회원가입 테스트
     */
    @Test
    @DisplayName("정상적인 회원가입 테스트")
    public void 정상적인_회원가입_테스트() {
        String userEmail = userService.signup(userRegister);

        assertThat(userEmail).isEqualTo(userMapper.findByUserEmail(userEmail).getUserEmail());
    }

    @Test
    @DisplayName("비밀번호 확인이 잘못되었을 때")
    public void 비밀번호_확인_오류_테스트() {
        UserRegister userRegister = new UserRegister(userEmail, userPassword, "q1w2e3r4!", name, phoneNumber);

        assertThrows(DifferentPasswordException.class, () -> userService.signup(userRegister));
    }

    @Test
    @DisplayName("회원가입 시, 중복 아이디 테스트")
    public void 중복_아이디_테스트() {
        userService.signup(userRegister);
        UserRegister userRegister2 = new UserRegister(userEmail, "q1w2e3r4!", "q1w2e3r4!", name, phoneNumber);

        assertThrows(ExistingIdException.class, () -> userService.signup(userRegister2));
    }

    /**
     * 로그인 테스트
     */
    @Test
    @DisplayName("정상적인 로그인 테스트")
    public void 정상적인_로그인_테스트() {
        UserRegister userRegister = new UserRegister(userEmail, userPassword, confirmUserPassword, name, phoneNumber);
        userService.signup(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest(userEmail, userPassword);
        User login = userService.login(loginRequest);

        assertThat(login.getUserEmail()).isEqualTo(userEmail);
        assertThat(login.getUserEmail()).isEqualTo(userMapper.findByUserEmailAndUserPassword(loginRequest).getUserEmail());
    }

    @Test
    @DisplayName("존재하지 않는 아이디 테스트")
    public void 로그인_실패_테스트1() {
        UserRegister userRegister = new UserRegister(userEmail, userPassword, confirmUserPassword, name, phoneNumber);
        userService.signup(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest("test3434@naver.com", "1q2w3e4r!");

        assertThrows(NotExistingIdException.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("아이디는 존재하나 비밀번호 오류 테스트")
    public void 로그인_실패_테스트2() {
        UserRegister userRegister = new UserRegister(userEmail, userPassword, confirmUserPassword, name, phoneNumber);
        userService.signup(userRegister);

        UserLoginRequest loginRequest = new UserLoginRequest("test1@naver.com", "11111");

        assertThrows(WrongPasswordException.class, () -> userService.login(loginRequest));
    }
}
