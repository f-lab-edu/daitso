package com.flab.daitso.service;

import com.flab.daitso.dto.user.*;
import com.flab.daitso.error.exception.ExistingIdException;
import com.flab.daitso.error.exception.NotExistingIdException;
import com.flab.daitso.error.exception.WrongPasswordException;
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

        // 고객 정보 저장
        userMapper.save(user);

        // 리스트 내 각 주소를 반복문으로 돌며 이메일(외래키)과 함께 저장
        userRegister.getAddress().forEach(address -> userMapper.saveAddress(user.getUserEmail(), address));

        // 결제수단이 하나라도 있을시 반복문을 돌며 이메일(외래키)과 함께 저장
        if (userRegister.getPaymentOptions() != null){
            userRegister.getPaymentOptions().forEach(paymentoption -> userMapper.savePaymentOption(user.getUserEmail(), paymentoption.getOption()));
        }
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

    public List<String> findAddressByEmail(String userEmail) {
        return userMapper.findAddressByEmail(userEmail);
    }

    public void changePassword(EmailPassword emailPassword) {

        // 객체 내 비밀번호 암호화
        emailPassword.setUserPassword(SHA256Util.getSHA256(emailPassword.getUserPassword()));
        // 비밀번호 변경
        userMapper.changePassword(emailPassword);
    }

    @Transactional
    public void addAddress(List<String> address, String userEmail) {

        // 리스트 내 각 주소를 반복문으로 돌며 이메일(외래키)과 함께 저장
        address.forEach(x -> userMapper.saveAddress(userEmail, x));

    }
}
