package com.flab.daitso.service;


import com.flab.daitso.dto.user.*;
import com.flab.daitso.error.exception.user.DifferentPasswordException;
import com.flab.daitso.error.exception.user.ExistingIdException;
import com.flab.daitso.error.exception.user.NotExistingIdException;
import com.flab.daitso.error.exception.user.WrongPasswordException;

import com.flab.daitso.mapper.UserMapper;
import com.flab.daitso.utils.SHA256Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public String signup(UserRegister userRegister) {
        validateDuplicateUser(userRegister);
        String encryptedPassword = SHA256Util.getSHA256(userRegister.getUserPassword());
        String encryptedConfirmPassword = SHA256Util.getSHA256(userRegister.getConfirmUserPassword());

        comparePassword(encryptedPassword, encryptedConfirmPassword);

        User user = new User.Builder()
                .userEmail(userRegister.getUserEmail())
                .userPassword(encryptedPassword)
                .name(userRegister.getName())
                .phoneNumber(userRegister.getPhoneNumber())
                .role(userRegister.getRole())
                .registrationDate(userRegister.getRegistrationDate())
                .build();

        // 고객 정보 저장
        userMapper.save(user);

        return user.getUserEmail();
    }

    private void validateDuplicateUser(UserRegister userRegister) {
        User findUser = userMapper.findByUserEmail(userRegister.getUserEmail());
        if (findUser != null) {
            throw new ExistingIdException();
        }
    }

    private void comparePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new DifferentPasswordException();
        }
    }

    public User login(UserLoginRequest userLoginRequest) {
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

    public void changePassword(EmailPassword emailPassword) {
        // 객체 내 비밀번호 암호화
        emailPassword.setUserPassword(SHA256Util.getSHA256(emailPassword.getUserPassword()));
        // 비밀번호 변경
        userMapper.changePassword(emailPassword);
    }

    public void addAddress(Address addressDto, int userId) {
        // 주소 정보가 담긴 addressDto 와 고유한 유저 식별자 userId 를 넘겨 db 에 새 주소 추가
        userMapper.saveAddress(userId, addressDto);
    }

    public User findByUserId(int userId) {
        return userMapper.findById(userId);
    }

    public List<Address> findAddressByUserId(int userId) {
        return userMapper.findAddressById(userId);
    }

    public User findByUserEmail(String userEmail) {
        return userMapper.findByUserEmail(userEmail);
    }
}
