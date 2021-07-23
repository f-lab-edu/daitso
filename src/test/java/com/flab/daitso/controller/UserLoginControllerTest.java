package com.flab.daitso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class UserLoginControllerTest {

    private static String userEmail = "test1@naver.com";
    private static String userPassword = "1q2w3e4r!";
    private static String confirmUserPassword = "1q2w3e4r!";
    private static String name = "park";
    private static String phoneNumber = "010-1111-2222";

    private static UserRegister userRegister = new UserRegister(userEmail, userPassword, confirmUserPassword, name, phoneNumber);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("로그인 테스트")
    public void 로그인_테스트() throws Exception {
        userService.signup(userRegister);

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);

        mvc.perform(post("/api/users/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 아이디, 로그인 실패 테스트")
    public void 로그인_실패_테스트() throws Exception {
        String userEmail = "test000@naver.com";
        String userPassword = "1q2w3e4r!";

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);

        mvc.perform(post("/api/users/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("NotExistingIdException"))
                .andDo(print());
    }

    @Test
    @DisplayName("아이디는 존재하지만 비밀번호 오류, 로그인 실패 테스트")
    public void 비밀번호_오류_로그인_실패_테스트() throws Exception {
        mvc.perform(post("/api/users/signup")
                .content(objectMapper.writeValueAsString(userRegister))
                .contentType(MediaType.APPLICATION_JSON));

        String userPassword = "1q2w3e4r!!";

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);

        mvc.perform(post("/api/users/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("WrongPasswordException"))
                .andDo(print());
    }

    @Test
    @DisplayName("세션이 존재할 때, 로그아웃 성공")
    public void 로그아웃_테스트() throws Exception {

        MockHttpSession session = new MockHttpSession();

        session.setAttribute("USER_ID", 1);

        mvc.perform(post("/api/users/logout")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("세션이 존재하지 않을 때, 로그아웃 실패")
    public void 로그아웃_실패_테스트() throws Exception {
        mvc.perform(post("/api/users/logout")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
