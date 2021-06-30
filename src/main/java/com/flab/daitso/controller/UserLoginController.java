package com.flab.daitso.controller;

import com.flab.daitso.dto.user.SessionUser;
import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.error.exception.user.UserNotLoginException;
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
        SessionUser sessionUser = new SessionUser(user);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("USER_ID", user.getUserEmail());
        session.setAttribute("USER", sessionUser);
        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (!isLogin(session)) {
            throw new UserNotLoginException();
        }
        session.removeAttribute("USER_ID");
        session.removeAttribute("USER");
    }

    public boolean isLogin(HttpSession httpSession) {
        return httpSession.getAttribute("USER_ID") != null;
    }
}
