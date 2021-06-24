package com.flab.daitso.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class UserRegister {

    private Long id;

    @NotBlank(message = "ID를 입력하세요") 
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{8,20}$",
    message = "비밀번호는 숫자, 특수문자, 영문 대/소문자가 모두 포함된 8 ~ 20자로 입력하세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 10, message = "이름은 10자 이내로 입력하세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp="^010-(\\d{4})-(\\d{4})$", message = "전화번호는 '-'를 포함하여 12자리로 입력하세요.")
    private String phoneNumber;

    private String registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public UserRegister(String userId, String userPassword, String name, String phoneNumber) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
