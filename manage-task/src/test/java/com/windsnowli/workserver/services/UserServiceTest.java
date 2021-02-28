package com.windsnowli.workserver.services;

import com.windsnowli.workserver.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserServiceTest {
    private StudentServiceImpl studentService;

    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Test
    public void testGetUserCount() {
        System.out.println(studentService.getClazzUserCount(1));
    }
}
