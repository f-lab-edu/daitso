package com.flab.daitso.mapper;

import com.flab.daitso.dto.user.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void save(User user);

    User findByUserEmail(String userEmail);

    User findByUserEmailAndUserPassword(UserLoginRequest userLoginRequest);

    void saveAddress(@Param("userId") int userId, @Param("addressDto") Address addressDto);

    List<Address> findAddressById(int userId);

    void changePassword(EmailPassword emailPassword);

    User findById(int userId);
}
