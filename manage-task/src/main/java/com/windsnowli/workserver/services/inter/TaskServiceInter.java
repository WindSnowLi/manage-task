package com.windsnowli.workserver.services.inter;

import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.entity.TaskRecord;
import com.windsnowli.workserver.entity.User;
import lombok.Builder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author windSnowLi
 */
public interface TaskServiceInter {
    /**
     * 通过ID查找任务
     *
     * @param taskId 任务ID
     * @return 返回任务对象
     */
    Task findTask(int taskId);

    /**
     * 通过包含任务ID的对象查找任务
     *
     * @param task 办好任务ID的任务对象
     * @return 返回任务对象
     */
    Task findTask(Task task);

    /**
     * 通过ID查找任务
     *
     * @param taskId 任务ID
     * @return 返回任务json格式的Msg信息
     */
    String findTaskJson(int taskId);


    /**
     * 通过ID查找任务
     *
     * @param task 任务对象
     * @return 返回任务json格式的Msg信息
     */
    String findTaskJson(Task task);

    /**
     * 通过班级ID查询所属任务
     *
     * @param clazzId 班级ID
     * @return 返回属于班级ID的任务集合
     */
    List<Task> findClazzTasks(int clazzId);

    /**
     * 通过班级ID查询所属任务
     *
     * @param clazzId 班级ID
     * @return 返回属于班级ID的任务集合Msg信息
     */
    String findClazzTasksJson(int clazzId);

    /**
     * 通过班级ID分页查询属于班级ID的任务集合
     *
     * @param clazzId     班级ID
     * @param currentPage 当前页
     * @param pageSize    页内容数量
     * @return 任务集合
     */
    List<Task> findClazzTasks(int clazzId, int currentPage, int pageSize);


    /**
     * 通过班级ID分页查询属于班级ID任务集合Msg信息
     *
     * @param clazzId     班级ID
     * @param currentPage 当前页
     * @param pageSize    页内容数量
     * @return 返回分页查询属于班级ID的任务集合Msg信息
     */
    String findClazzTasksJson(int clazzId, int currentPage, int pageSize);


    /**
     * 根据学院ID查找任务集合
     *
     * @param academyId 学院ID
     * @return 所属学院任务集合
     */
    List<Task> findAcademyTasks(int academyId);


    /**
     * 通过学院ID分页查询属于学院ID任务集合
     *
     * @param academyId   学院ID
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 返回分页查询属于学院ID的任务集合
     */
    List<Task> findAcademyTasks(int academyId, int currentPage, int pageSize);


    /**
     * 通过学院ID分页查询属于学院ID任务集合Msg信息
     *
     * @param academyId   学院ID
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 返回分页查询属于学院ID的任务集合Msg信息
     */
    String findAcademyTasksJson(int academyId, int currentPage, int pageSize);


    /**
     * 根据学院ID查找任务集合Msg信息
     *
     * @param academyId 学院ID
     * @return 所属学院任务集合Msg信息
     */
    String findAcademyTasksJson(int academyId);

    /**
     * 插入任务
     *
     * @param task 插入任务的对象
     * @return 插入结果，Msg信息
     */
    String addTaskJson(Task task);

    /**
     * 插入任务
     *
     * @param task 插入任务的对象
     * @return 插入结果为插入影响的行数
     */
    int addTask(Task task);


    /**
     * 通过任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除结果，Msg信息
     */
    String deleteTaskJson(int taskId);

    /**
     * 通过任务对象删除任务
     *
     * @param task 任务对象
     * @return 删除结果，Msg信息
     */
    String deleteTaskJson(Task task);

    /**
     * 通过任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除条数
     */
    int deleteTask(int taskId);

    /**
     * 通过任务对象删除任务
     *
     * @param task 任务对象
     * @return 删除条数
     */
    int deleteTask(Task task);


    /**
     * 通过任务对象ID更新任务信息
     *
     * @param task 任务对象
     * @return 影响的条数
     */
    int updateTask(Task task);


    /**
     * 通过任务对象ID更新任务信息
     *
     * @param task 任务对象
     * @return 修改结果Msg信息
     */
    String updateTaskJson(Task task);


    /**
     * 查询所属学院任务总数
     *
     * @param academyId 学院ID
     * @return 任务总数
     */
    String getAcademyTaskCount(int academyId);

    /**
     * 查询所属班级任务总数
     *
     * @param clazzId 班级ID
     * @return 任务总数
     */
    String getClazzTaskCount(int clazzId);


    /**
     * 获取任务完成名单分页查询
     *
     * @param taskId 任务ID
     * @param start  开始项
     * @param end    结束项
     * @return 用户列表
     */
    List<User> getTaskCompleteRecordPage(int taskId, int start, int end);


    /**
     * 获取任务完成名单分页查询
     *
     * @param taskId 任务ID
     * @param start  开始项
     * @param end    结束项
     * @return 用户列表
     */
    String getTaskCompleteRecordPageJson(int taskId, int start, int end);


    /**
     * 获取任务完成人数
     *
     * @param taskId 任务ID
     * @return 人数
     */
    int getTaskCompleteUserCount(int taskId);

    /**
     * 获取任务完成人数
     *
     * @param taskId 任务ID
     * @return 人数Msg信息
     */
    String getTaskCompleteUserCountJson(int taskId);


    /**
     * 任务完成度
     *
     * @param taskId 任务ID
     * @return 任务完成度，单位百分比
     */
    double getTaskCompleteRate(int taskId);

    /**
     * 任务完成度
     *
     * @param taskId 任务ID
     * @return 任务完成度，单位百分比Msg信息
     */
    String getTaskCompleteRateJson(int taskId);


    /**
     * 获取任务未完成人数
     *
     * @param taskId 任务ID
     * @return 人数
     */
    int getTaskUnfinishedCount(int taskId);


    /**
     * 获取任务未完成人数
     *
     * @param taskId 任务ID
     * @return 人数Msg信息
     */
    String getTaskUnfinishedCountJson(int taskId);

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 插入条数
     */
    int completeTask(int taskId, int userId);


    /**
     * 班级已经结束任务总数
     *
     * @param clazzId 班级ID
     * @return 数量
     */
    int getClazzCompleteTaskCount(int clazzId);

    /**
     * 学院已经结束任务总数
     *
     * @param academyId 学院ID
     * @return 数量
     */
    int getAcademyCompleteTaskCount(int academyId);


    /**
     * 班级已经结束任务总数
     *
     * @param clazzId 班级ID
     * @return 数量Msg信息
     */
    String getClazzCompleteTaskCountJson(int clazzId);

    /**
     * 学院已经结束任务总数
     *
     * @param academyId 学院ID
     * @return 数量Msg信息
     */
    String getAcademyCompleteTaskCountJson(int academyId);


    /**
     * 班级正在进行任务总数
     *
     * @param clazzId 班级ID
     * @return 数量
     */
    int getClazzStartingTaskCount(int clazzId);

    /**
     * 学院正在进行任务总数
     *
     * @param academyId 学院ID
     * @return 数量
     */
    int getAcademyStartingTaskCount(int academyId);


    /**
     * 班级正在进行任务总数
     *
     * @param clazzId 班级ID
     * @return 数量Msg信息
     */
    String getClazzStartingTaskCountJson(int clazzId);

    /**
     * 学院正在进行任务总数
     *
     * @param academyId 学院ID
     * @return 数量Msg信息
     */
    String getAcademyStartingTaskCountJson(int academyId);


    /**
     * 查询用户完成报告信息
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务完成报告对象
     */
    TaskRecord findTaskRecord(int taskId,int userId);
}
