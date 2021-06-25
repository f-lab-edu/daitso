package com.flab.daitso.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @Email
    @NotBlank(message = "ID를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
