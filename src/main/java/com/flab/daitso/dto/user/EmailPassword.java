package com.flab.daitso.dto.user;

import javax.validation.constraints.Pattern;

public class EmailPassword {

    private final int userId;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{8,15}$",
            message = "비밀번호는 숫자,특수문자,영문 대/소문자가 모두 포함된 8~15자리로 입력하세요.")
    private String userPassword;

    public EmailPassword(int userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

}
