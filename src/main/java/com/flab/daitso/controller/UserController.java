package com.flab.daitso.controller;

import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void join(@RequestBody @Valid UserRegister userRegister) {
        userService.join(userRegister);
    }
}
