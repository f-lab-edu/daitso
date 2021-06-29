package com.flab.daitso.service;

import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.error.exception.ExistingIdException;
import com.flab.daitso.error.exception.NotExistingIdException;
import com.flab.daitso.error.exception.WrongPasswordException;
import com.flab.daitso.mapper.UserMapper;
import com.flab.daitso.utils.SHA256Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

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
        User findUser = userMapper.findByUserEmail(userRegister.getUserEmail());
        if (findUser != null) {
            throw new ExistingIdException();
        }
    }

    public User login(@Valid UserLoginRequest userLoginRequest) {
        return checkUser(userLoginRequest);
    }

    private void checkExistingEmail(String userEmail) {
        User byUserEmail = userMapper.findByUserEmail(userEmail);
        if (byUserEmail == null) {
            throw new NotExistingIdException();
        }
    }

    private User checkUser(UserLoginRequest userLoginRequest) {
        checkExistingEmail(userLoginRequest.getUserEmail());
        String encryptedPassword = SHA256Util.getSHA256(userLoginRequest.getUserPassword());

        userLoginRequest.setUserPassword(encryptedPassword);
        User byUserEmailAndUserPassword = userMapper.findByUserEmailAndUserPassword(userLoginRequest);
        if (byUserEmailAndUserPassword == null) {
            throw new WrongPasswordException();
        }
        return byUserEmailAndUserPassword;
    }
}
