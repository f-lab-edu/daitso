package com.flab.daitso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.daitso.dto.user.UserRegister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class UserControllerTest {

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
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    public void 회원가입() throws Exception {
        mvc.perform(post("/api/users/signup")
                .content(objectMapper.writeValueAsString(userRegister))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입")
    public void 존재하는_이메일_회원가입() throws Exception {
        mvc.perform(post("/api/users/signup")
                .content(objectMapper.writeValueAsString(userRegister))
                .contentType(MediaType.APPLICATION_JSON));

        UserRegister userRegister2 = new UserRegister(userEmail, "1q2w3e4r!!!", "1q2w3e4r!!!", "hesong", "010-3333-4444");

        mvc.perform(post("/api/users/signup")
                .content(objectMapper.writeValueAsString(userRegister2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ExistingIdException"))
                .andDo(print());
    }
}
