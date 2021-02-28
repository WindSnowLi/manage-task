package com.windsnowli.workserver.mapper;


import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserServiceImpl loginService;
    @Test
    public void LoginTest(){
        User user=new User();
        user.setUserNumber("20211111");
        user.setUserPassword("111111");
        System.out.println(loginService.loginJson(user));
    }
}
