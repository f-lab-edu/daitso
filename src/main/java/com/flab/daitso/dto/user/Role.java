package com.flab.daitso.dto.user;

public enum Role {

    GUEST("ROLE_GUEST", "비회원 사용자"),
    USER("ROLE_USER", "일반 사용자"),
    MEMBER("ROLE_MEMBER", "회원 사용자"),
    MANAGER("ROLE_MANAGER", "관리자");

    private final String key;
    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
