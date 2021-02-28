package com.windsnowli.workserver.services;

import com.alibaba.fastjson.JSONObject;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskServiceTest {
    private TaskServiceImpl taskService;

    @Autowired
    public void setTaskService(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @Test
    public void testGetTaskCompleteRate() {
        System.out.println(taskService.getTaskCompleteRate(3));
    }

    @Test
    public void testGetTaskCompleteRecordPageJson() {
        System.out.println(taskService.getTaskCompleteRecordPageJson(4, 1, 30));
    }
}
