package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.exception.ExistingIdException;
import com.flab.daitso.mapper.UserMapper;
import com.flab.daitso.utils.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public String join(@Valid UserRegister userRegister) {
        validateDuplicateUser(userRegister);
        userRegister.setUserPassword(SHA256Util.getSHA256(userRegister.getUserPassword()));
        User user = User.builder()
                .id(userRegister.getId())
                .userId(userRegister.getUserId())
                .userPassword(userRegister.getUserPassword())
                .name(userRegister.getName())
                .phoneNumber(userRegister.getPhoneNumber())
                .registrationDate(userRegister.getRegistrationDate())
                .build();
        userMapper.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(UserRegister userRegister) {
        User findUser = userMapper.findByUserId(userRegister.getUserId());
        Optional<User> optionalUser = Optional.ofNullable(findUser);
        if (optionalUser.isPresent()) {
            throw new ExistingIdException();
        }
    }
}
