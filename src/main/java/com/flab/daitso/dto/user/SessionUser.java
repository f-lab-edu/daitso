package com.flab.daitso.dto.user;

import java.io.Serializable;

public class SessionUser implements Serializable {

    private String userEmail;
    private String name;
    private String phoneNumber;

    public SessionUser(User user) {
        this.userEmail = user.getUserEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Stri성ng getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
