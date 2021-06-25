package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.exception.ExistingIdException;
import com.flab.daitso.exception.NotExistingIdException;
import com.flab.daitso.exception.WrongPasswordException;
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
                .role(userRegister.getRole())
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

    public User login(@Valid UserLoginRequest userLoginRequest) {
        return checkUser(userLoginRequest);
    }

    private void checkExistingId(String userId) {
        User byUserId = userMapper.findByUserId(userId);
        Optional<User> findUserId = Optional.ofNullable(byUserId);
        if (!findUserId.isPresent()) {
            throw new NotExistingIdException();
        }
    }

    private User checkUser(UserLoginRequest userLoginRequest) {
        checkExistingId(userLoginRequest.getUserId());
        userLoginRequest.setUserPassword(SHA256Util.getSHA256(userLoginRequest.getUserPassword()));
        User byUserIdAndUserPassword = userMapper.findByUserIdAndUserPassword(userLoginRequest);
        Optional<User> findUser = Optional.ofNullable(byUserIdAndUserPassword);
        if (!findUser.isPresent()) {
            throw new WrongPasswordException();
        }
        return byUserIdAndUserPassword;
    }

}
