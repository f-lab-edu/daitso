package com.flab.daitso.controller;

import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void join(@RequestBody @Valid UserRegister userRegister) {
        userService.signup(userRegister);
    }
}
