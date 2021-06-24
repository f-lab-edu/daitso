package com.flab.daitso.mapper;

import com.flab.daitso.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void save(User user);

    User findByUserId(String userId);
}
