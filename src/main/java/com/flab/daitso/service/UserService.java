package com.flab.daitso.service;


import com.flab.daitso.dto.user.User;
import com.flab.daitso.dto.user.UserLoginRequest;
import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.error.exception.user.ExistingIdException;
import com.flab.daitso.error.exception.user.NotExistingIdException;
import com.flab.daitso.error.exception.user.WrongPasswordException;

import com.flab.daitso.mapper.UserMapper;
import com.flab.daitso.utils.SHA256Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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

    public User findByEmail(String email) {
        return userMapper.findByUserEmail(email);
    }

    public List<Address> findAddressById(int userId) {
        return userMapper.findAddressById(userId);
    }

    public void changePassword(EmailPassword emailPassword) {
        // 객체 내 비밀번호 암호화
        emailPassword.setUserPassword(SHA256Util.getSHA256(emailPassword.getUserPassword()));
        // 비밀번호 변경
        userMapper.changePassword(emailPassword);
    }

    @Transactional
    public void addAddress(Address addressDto, int userId) {
        // 주소 정보가 담긴 addressDto 와 고유한 유저 식별자 userId 를 넘겨 db 에 새 주소 추가
        userMapper.saveAddress(userId, addressDto);
    }

    public List<Integer> findPaymentOption(String userEmail) {
        return userMapper.findPaymentOption(userEmail);
    }

    public User findById(int userId) {
        return userMapper.findById(userId);
    }
}
