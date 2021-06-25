package com.flab.daitso.controller;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(UserLoginRequest userLoginRequest, HttpSession httpSession) {
        User user = userService.login(userLoginRequest);
        return user;
    }

}
