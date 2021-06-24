package com.flab.daitso.service;

import com.flab.daitso.dto.user.UserRegister;
import com.flab.daitso.exception.ExistingIdException;
import com.flab.daitso.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("정상적인 회원가입 테스트")
    public void 정상적인_회원가입_테스트() {
        UserRegister userRegister = new UserRegister("test1", "1q2w3e4r!", "test", "010-1111-2222");
        String userId = userService.join(userRegister);
        assertThat(userId).isEqualTo(userMapper.findByUserId("test1").getUserId());
    }

    @Test
    @DisplayName("중복 아이디 테스트")
    public void 중복_아이디_테스트() {
        UserRegister userRegister1 = new UserRegister("test1", "1q2w3e4r!", "test", "010-1111-2222");
        UserRegister userRegister2 = new UserRegister("test1", "1q2w3e4r!", "test", "010-1111-2222");
        userService.join(userRegister1);

        assertThrows(ExistingIdException.class, () -> userService.join(userRegister2));
    }
}
