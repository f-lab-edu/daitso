package com.flab.daitso.controller;

import com.flab.daitso.dto.user.SessionUser;
import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.error.exception.UserNotLoginException;
import com.flab.daitso.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {

        User user = userService.login(userLoginRequest);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("USER_ID", userLoginRequest.getUserEmail());
        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (!isLogin(session)) {
            throw new UserNotLoginException();
        }
        session.removeAttribute("USER_ID");
    }

    public boolean isLogin(HttpSession httpSession) {
        return httpSession.getAttribute("USER_ID") != null;
    }
}
