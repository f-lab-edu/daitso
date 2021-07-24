package com.flab.daitso.dto.user;

public class Address {

    private final String zipcode; // 우편번호
    private final String si; // 시
    private final String gu;  // 구
    private final String dong; // 동
    private final String address; // 상세주소
    private final int userId; // 외래키(user 테이블 pk)

    public Address(String zipcode, String si, String gu, String dong, String address, int userId) {
        this.zipcode = zipcode;
        this.si = si;
        this.gu = gu;
        this.dong = dong;
        this.address = address;
        this.userId = userId;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getSi() {
        return si;
    }

    public String getGu() {
        return gu;
    }

    public String getDong() {
        return dong;
    }

    public String getAddress() {
        return address;
    }

    public int getUserId() {
        return userId;
    }

}
