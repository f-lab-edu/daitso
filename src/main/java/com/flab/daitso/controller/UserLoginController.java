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

    @GetMapping("logout")
    public void logout(HttpServletRequest request){
        // 쿠키에 있는 세션 아이디에 해당되는 세션이 없는 경우 새로 생성하지 않고 null 값 받음
        HttpSession session = request.getSession(false);
        // null 이면 로그인 상태가 아님
        if (session == null){
            throw new UserNotLoginException();
        }
        // 해당 세션을 무효화 하여 같은 세션 아이디로 검색시 null 값 받도록 함
        session.invalidate();

    }

}
