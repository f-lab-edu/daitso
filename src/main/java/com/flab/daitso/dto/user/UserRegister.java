package com.flab.daitso.dto.user;

import com.flab.daitso.utils.SHA256Util;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class UserRegister {

    @NotBlank(message = "Email을 입력하세요.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{8,20}$",
            message = "비밀번호는 숫자, 특수문자, 영문 대/소문자가 모두 포함된 8 ~ 20자로 입력하세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 10, message = "이름은 10자 이내로 입력하세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$",
            message = "전화번호는 13자리 이내로 입력하세요.")
    private String phoneNumber;

    private Role role = Role.USER;

    private LocalDateTime registrationDate = LocalDateTime.now();

    public UserRegister(String userEmail, String userPassword, String name, String phoneNumber) {
        this.userEmail = userEmail;
        this.userPassword = encryptPassword(userPassword);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private String encryptPassword(String userPassword) {
        return SHA256Util.getSHA256(userPassword);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
