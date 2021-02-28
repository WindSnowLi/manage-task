package com.windsnowli.workserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.windsnowli.workserver.annotation.PowerLevel;
import com.windsnowli.workserver.annotation.UserLoginToken;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author windSnowLi
 */
@RestController
@RequestMapping("/task")
@UserLoginToken
public class TaskController {
    private TaskServiceImpl taskService;

    @Autowired
    public void setTaskService(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    /**
     * 通过任务ID查询任务信息
     *
     * @param taskJson 任务json信息,格式为{"taskId":""}
     * @return 任务Msg信息
     */
    @PostMapping(value = "findTaskJson", produces = "application/json;charset=UTF-8")
    public String findTaskJson(@RequestBody JSONObject taskJson) {
        return taskService.findTaskJson(JSONObject.parseObject(taskJson.toJSONString(), Task.class));
    }

    /**
     * 通过班级ID分页查询属于班级ID任务集合Msg信息
     *
     * @param taskJson 分页查询信息,请求格式{"clazzId":"int","currentPage":"int" ,"pageSize":"int" }
     * @return 属于班级ID任务集合Msg信息
     */
    @PostMapping(value = "findClazzTasksPageJson", produces = "application/json;charset=UTF-8")
    public String findClazzTasksPageJson(@RequestBody JSONObject taskJson) {
        return taskService.findClazzTasksJson(taskJson.getInteger("clazzId"), taskJson.getInteger("currentPage"), taskJson.getInteger("pageSize"));
    }

    /**
     * 通过班级ID查询属于班级ID任务集合Msg信息
     *
     * @param taskJson 请求格式{"clazzId":"int"}
     * @return 属于班级ID任务集合Msg信息
     */
    @PostMapping(value = "findClazzAllTasksJson", produces = "application/json;charset=UTF-8")
    public String findClazzAllTasksJson(@RequestBody JSONObject taskJson) {
        return taskService.findClazzTasksJson(taskJson.getInteger("clazzId"));
    }

    /**
     * 通过学院ID查询属于学院ID任务集合Msg信息
     *
     * @param taskJson 请求格式{"academyId":"int"}
     * @return 属于班级ID任务集合Msg信息
     */
    @PostMapping(value = "findAcademyAllTasksJson", produces = "application/json;charset=UTF-8")
    public String findAcademyAllTasksJson(@RequestBody JSONObject taskJson) {
        return taskService.findAcademyTasksJson(taskJson.getInteger("academyId"));
    }

    /**
     * 通过学院ID分页查询属于学院ID任务集合Msg信息
     *
     * @param taskJson 分页查询信息,请求格式{"academyId":"int","currentPage":"int" ,"pageSize":"int" }
     * @return 属于班级ID任务集合Msg信息
     */
    @PostMapping(value = "findAcademyTasksPageJson", produces = "application/json;charset=UTF-8")
    public String findAcademyTasksPageJson(@RequestBody JSONObject taskJson) {
        return taskService.findAcademyTasksJson(taskJson.getInteger("academyId"), taskJson.getInteger("currentPage"), taskJson.getInteger("pageSize"));
    }


    /**
     * 通过任务json信息插入任务
     *
     * @param taskJson 任务json信息
     * @return 插入结果Msg信息
     */
    @PowerLevel(value = 1)
    @PostMapping(value = "addTaskJson", produces = "application/json;charset=UTF-8")
    public String addTaskJson(@RequestBody JSONObject taskJson) {
        return taskService.addTaskJson(JSONObject.parseObject(taskJson.toJSONString(), Task.class));
    }

    /**
     * 通过任务ID信息删除任务
     *
     * @param taskJson 任务json信息,格式为{"taskId":""}
     * @return 删除结果Msg信息
     */
    @PowerLevel(value = 1)
    @PostMapping(value = "deleteTaskJson", produces = "application/json;charset=UTF-8")
    public String deleteTaskJson(@RequestBody JSONObject taskJson) {
        return taskService.deleteTaskJson(JSONObject.parseObject(taskJson.toJSONString(), Task.class));
    }

    /**
     * 通过task对象json串更新信息
     *
     * @param taskJson task对象json串
     * @return 更新状态
     */
    @PowerLevel(value = 1)
    @PostMapping(value = "updateTaskJson", produces = "application/json;charset=UTF-8")
    public String updateTaskJson(@RequestBody JSONObject taskJson) {
        return taskService.updateTaskJson(JSONObject.parseObject(taskJson.toJSONString(), Task.class));
    }


    /**
     * 查询所属学院任务总数
     *
     * @param taskJson 学院ID信息,格式{"academyId":"int"}
     * @return 任务总数Msg信息
     */
    @PostMapping(value = "getAcademyTaskCount", produces = "application/json;charset=UTF-8")
    public String getAcademyTaskCount(@RequestBody JSONObject taskJson) {
        return taskService.getAcademyTaskCount(taskJson.getInteger("academyId"));
    }

    /**
     * 查询所属班级任务总数
     *
     * @param taskJson 班级ID信息，格式{"clazzId":"int"}
     * @return 任务总数Msg信息
     */
    @PostMapping(value = "getClazzTaskCount", produces = "application/json;charset=UTF-8")
    public String getClazzTaskCount(@RequestBody JSONObject taskJson) {
        return taskService.getClazzTaskCount(taskJson.getInteger("clazzId"));
    }


    /**
     * 获取任务完成名单
     *
     * @param taskJson 任务json信息,格式为{"taskId":"int","currentPage":"int" ,"pageSize":"int" }
     * @return 用户列表Msg信息
     */
    @PostMapping(value = "getTaskCompleteRecordPageJson", produces = "application/json;charset=UTF-8")
    public String getTaskCompleteRecordPageJson(@RequestBody JSONObject taskJson) {
        return taskService.getTaskCompleteRecordPageJson(taskJson.getInteger("taskId"), taskJson.getInteger("currentPage"), taskJson.getInteger("pageSize"));
    }

    /**
     * 任务完成度
     *
     * @param taskJson 任务ID,格式为{"taskId":"int"}
     * @return 任务完成度，单位百分比Msg信息
     */
    @PostMapping(value = "getTaskCompleteRateJson", produces = "application/json;charset=UTF-8")
    public String getTaskCompleteRateJson(@RequestBody JSONObject taskJson) {
        return taskService.getTaskCompleteRateJson(taskJson.getInteger("taskId"));
    }


    /**
     * 获取任务未完成人数
     *
     * @param taskJson 任务ID,格式为{"taskId":"int"}
     * @return 人数Msg信息
     */
    @PostMapping(value = "getTaskUnfinishedCountJson", produces = "application/json;charset=UTF-8")
    public String getTaskUnfinishedCountJson(@RequestBody JSONObject taskJson) {
        return taskService.getTaskUnfinishedCountJson(taskJson.getInteger("taskId"));
    }


    /**
     * 获取任务完成人数
     *
     * @param taskJson 任务ID,格式为{"taskId":"int"}
     * @return 人数Msg信息
     */
    @PostMapping(value = "getTaskCompleteUserCountJson", produces = "application/json;charset=UTF-8")
    public String getTaskCompleteUserCountJson(@RequestBody JSONObject taskJson) {
        return taskService.getTaskCompleteUserCountJson(taskJson.getInteger("taskId"));
    }


    /**
     * 班级已经结束任务总数
     *
     * @param taskJson 班级ID 格式{"clazzId":"int"}
     * @return 数量Msg信息
     */
    @PostMapping(value = "getClazzCompleteTaskCountJson", produces = "application/json;charset=UTF-8")
    public String getClazzCompleteTaskCountJson(@RequestBody JSONObject taskJson) {
        return taskService.getClazzCompleteTaskCountJson(taskJson.getInteger("clazzId"));
    }

    /**
     * 学院已经结束任务总数
     *
     * @param taskJson 学院ID 格式{"academyId":"int"}
     * @return 数量Msg信息
     */
    @PostMapping(value = "getAcademyCompleteTaskCountJson", produces = "application/json;charset=UTF-8")
    public String getAcademyCompleteTaskCountJson(@RequestBody JSONObject taskJson) {
        return taskService.getAcademyCompleteTaskCountJson(taskJson.getInteger("academyId"));
    }


    /**
     * 班级正在进行任务总数
     *
     * @param taskJson 班级ID
     * @return 数量Msg信息
     */
    @PostMapping(value = "getClazzStartingTaskCountJson", produces = "application/json;charset=UTF-8")
    public String getClazzStartingTaskCountJson(@RequestBody JSONObject taskJson) {
        return Msg.getSuccessMsg(taskService.getClazzStartingTaskCount(taskJson.getInteger("clazzId")));
    }

    /**
     * 学院正在进行任务总数
     *
     * @param taskJson 学院ID
     * @return 数量Msg信息
     */
    @PostMapping(value = "getAcademyStartingTaskCountJson", produces = "application/json;charset=UTF-8")
    public String getAcademyStartingTaskCountJson(@RequestBody JSONObject taskJson) {
        return Msg.getSuccessMsg(taskService.getAcademyStartingTaskCount(taskJson.getInteger("academyId")));
    }
}
