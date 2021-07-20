package com.flab.daitso.controller;

/*import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.service.UserService;
import org.assertj.core.api.Assertions;
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
        String userEmail = "test13@naver.com";
        String userPassword = "1q2w3e4r!";

        UserRegister userRegister = new UserRegister(userEmail, userPassword, "test", "010-1111-2222","seoul");
        userService.signup(userRegister);

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);

        mvc.perform(post("/users/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 아이디 로그인 실패 테스트")
    public void 로그인_실패_테스트() throws Exception {
        String userEmail = "test000@naver.com";
        String userPassword = "1q2w3e4r!";

        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);

        mvc.perform(post("/users/login")
                .content(objectMapper.writeValueAsString(userLoginRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("NotExistingIdException"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 성공")
    public void 로그아웃_테스트() throws Exception {

        MockHttpSession session = new MockHttpSession();

        session.setAttribute("USER_ID", 1);

        mvc.perform(post("/users/logout")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}*/
