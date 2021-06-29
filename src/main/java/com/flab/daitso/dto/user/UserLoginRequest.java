package com.flab.daitso.dto.user;

import com.flab.daitso.utils.SHA256Util;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserLoginRequest {

    @Email
    @NotBlank(message = "Email을 입력해주세요.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;

    public UserLoginRequest(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = encryptPassword(userPassword);
    }

    private String encryptPassword(String userPassword) {
        return SHA256Util.getSHA256(userPassword);
    }

    public String getUserId() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
