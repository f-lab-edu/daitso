package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.exception.ExistingIdException;
import com.flab.daitso.exception.NotExistingIdException;
import com.flab.daitso.exception.WrongPasswordException;
import com.flab.daitso.mapper.UserMapper;
import com.flab.daitso.utils.SHA256Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    public String signup(@Valid UserRegister userRegister) {
        validateDuplicateUser(userRegister);
        String encryptedPassword = SHA256Util.getSHA256(userRegister.getUserPassword());

        User user = new User.Builder()
                .userEmail(userRegister.getUserEmail())
                .userPassword(encryptedPassword)
                .name(userRegister.getName())
                .phoneNumber(userRegister.getPhoneNumber())
                .role(userRegister.getRole())
                .registrationDate(userRegister.getRegistrationDate())
                .build();

        userMapper.save(user);
        return user.getUserEmail();
    }

    private void validateDuplicateUser(UserRegister userRegister) {
        User findUser = userMapper.findByUserId(userRegister.getUserEmail());
        Optional<User> optionalUser = Optional.ofNullable(findUser);
        if (optionalUser.isPresent()) {
            throw new ExistingIdException();
        }
    }

    public User login(@Valid UserLoginRequest userLoginRequest) {
        return checkUser(userLoginRequest);
    }

    private void checkExistingId(String userEmail) {
        User byUserId = userMapper.findByUserId(userEmail);
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
