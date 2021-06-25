package com.flab.daitso.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String userId;
    private String userPassword;
    private String name;
    private String phoneNumber;
    private Role role;
    private String registrationDate;

    @Builder
    public User(Long id, String userId, String userPassword, String name, String phoneNumber, Role role, String registrationDate) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.registrationDate = registrationDate;
    }
}
