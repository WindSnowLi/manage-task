package com.windsnowli.workserver.utils;

import com.windsnowli.workserver.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUtils {
    @Test
    public void testJwt() {
        User user = new User();
        user.setUserId(2);
        user.setUserPower(1);
        String token = JwtUtils.getToken(user);
        System.out.println(token);
        System.out.println(JwtUtils.getTokenUserId(token));
    }

    @Test
    public void testSha512ToMd5() {
        //b8d9bcc536b2a769db391ba24fa60111
        System.out.println(CodeUtils.getSha512ToMd5("20218888", "20218888"));
    }
}
