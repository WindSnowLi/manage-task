package com.windsnowli.workserver.entity;

import com.windsnowli.workserver.config.AliyunOssConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAliyunOssConfig {
    @Autowired
    private AliyunOssConfig aliyunOssConfig;

    @Test
    public void testAliyunOss() {
        System.out.println(aliyunOssConfig.toString());
    }
}
