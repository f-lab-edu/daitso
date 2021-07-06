package com.flab.daitso.mapper;

import com.flab.daitso.dto.user.EmailPassword;
import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void save(User user);

    User findByUserEmail(String userEmail);

    User findByUserEmailAndUserPassword(UserLoginRequest userLoginRequest);

    void saveAddress(@Param("userEmail") String userEmail, @Param("address") String address);

    List<String> findAddressByEmail(String userEmail);

    void changePassword(EmailPassword emailPassword);

    void savePaymentOption(@Param("userEmail") String userEmail, @Param("paymentoption") int option);
}
