package com.flab.daitso.dto.user;

import java.time.LocalDateTime;

public class User {

    private Long userId;
    private String userEmail;
    private String userPassword;
    private String name;
    private String phoneNumber;
    private Role role;
    private LocalDateTime registrationDate;

    public User(Long userId, String userEmail, String userPassword, String name, String phoneNumber, Role role, LocalDateTime registrationDate) {
        this.userId = 1L;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.registrationDate = registrationDate;
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

    /**
     * Builder 패턴 구현
     */
    public static class Builder {
        private String userEmail;
        private String userPassword;
        private String name;
        private String phoneNumber;
        private Role role;
        private LocalDateTime registrationDate;

        public Builder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder registrationDate(LocalDateTime registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public User build() {
            return new User(1L, userEmail, userPassword, name, phoneNumber, role, registrationDate);
        }
    }
}
