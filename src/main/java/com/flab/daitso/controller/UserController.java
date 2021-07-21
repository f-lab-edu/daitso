package com.flab.daitso.controller;

import com.flab.daitso.dto.user.*;

import com.flab.daitso.error.exception.user.UserNotLoginException;
import com.flab.daitso.error.exception.user.WrongPasswordException;
import com.flab.daitso.service.UserService;
import com.flab.daitso.utils.SHA256Util;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserRegister userRegister) {
        userService.signup(userRegister);
    }

    /**
     * 로그인된 상태에서 개인 정보를 검색하는 기능
     * @param password 본인 확인을 한번 더 확인하기 위한 비밀번호
     */
    @PostMapping("/mypage/myinfo")
    public Myinfo myinfo(@RequestBody HashMap<String, String> password, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        // 로그인 상태가 아니다
        if (session == null){
            throw new UserNotLoginException();
        }
        // 세션에 저장된 유저 아이디를 가져와 아이디로 해당 유저 검색
        int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());
        User user = userService.findById(userId);
        // 마이정보를 보기위해 다시 한번 비밀번호로 본인 인증
        if (!user.getUserPassword().equals(SHA256Util.getSHA256(password.get("userPassword")))){
            throw new WrongPasswordException();
        }
        // 해당 회원이 등록한 주소를 이메일을 이용해 모두 검색해 가져온다
        List<Address> address = userService.findAddressById(userId);

        // Myinfo 객체에 개인 정보를 담아 리턴
        return new Myinfo(user.getUserEmail(), user.getName(), user.getPhoneNumber(), address);
    }

    /**
     * 로그인된 상태에서 비밀번호를 변경하는 기능
     * @param passwords 현재 비밀번호, 새 비밀번호, 재입력된 새 비밀번호 3개가 담겨있는 해쉬맵
     *                  각 해쉬 키 -> "userPassword", "newPassword1", "newPassword2"
     */
    @PostMapping("/mypage/changepassword")
    public void changePassword(@RequestBody HashMap<String, String> passwords, HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session == null){
            throw new UserNotLoginException();
        }

        // 새 비밀번호와 재입력된 새 비밀번호를 꺼낸다
        String newPassword1 = passwords.get("newPassword1");
        String newPassword2 = passwords.get("newPassword2");

        // 새 비밀번호와 재입력한 새 비밀번호가 일치 하는지 검증
        if (!newPassword1.equals(newPassword2)){
            throw new WrongPasswordException();
        }

        int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());
        User user = userService.findById(userId);

        // 비밀번호 변경을 위해 다시 한번 비밀번호로 본인 인증
        if (!user.getUserPassword().equals(SHA256Util.getSHA256(passwords.get("userPassword")))){
            throw new WrongPasswordException();
        }

        // 새 비밀번호와 이메일을 담은 객체를 이용해(새 비밀번호가 비밀번호 형태 조건 따르도록 함) 해당 유저 비밀번호 변경
        userService.changePassword(new EmailPassword(userId, newPassword1));
    }

    /**
     * 로그인된 상태에서 주소 목록 검색
     */
    @GetMapping("/mypage/address")
    public List<Address> findAddress(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session == null){
            throw new UserNotLoginException();
        }

        int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());
        return userService.findAddressById(userId);
    }

    /**
     * 로그인된 상태에서 새 주소 추가
     *  @param addressDto 새 주소가 들어있는 HashMap<String>
     */
    @PostMapping("/mypage/addaddress")
    public void addAddress(@RequestBody Address addressDto, HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null){
            throw new UserNotLoginException();
        }

        int userId = Integer.parseInt(session.getAttribute("USER_ID").toString());
        // 새 주소 등록
        userService.addAddress(addressDto, userId);
        // 새 주소 등록후 주소 목록 검색하기 위해 리다이렉트
        response.sendRedirect("/api/users/mypage/address");
    }
}
