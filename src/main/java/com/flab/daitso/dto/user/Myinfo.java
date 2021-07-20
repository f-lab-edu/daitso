package com.flab.daitso.dto.user;

import java.util.List;

public class Myinfo {

    private final String userEmail;
    private final String userName;
    private final String phoneNumber;
    private final List<Address> userAddress;

    public Myinfo(String userEmail, String userName, String phoneNumber, List<Address> userAddress) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Address> getUserAddress() {
        return userAddress;
    }

    @Override
    public String toString() {
        return "Myinfo{" +
                "userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userAddress=" + userAddress +
                '}';
    }
}
