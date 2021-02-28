package com.windsnowli.workserver.mapper;

import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author windSnowLi
 */
@Mapper
@Repository
public interface TaskMapper {
    /**
     * 通过ID查找任务
     *
     * @param taskId 任务ID
     * @return 任务对象
     */
    @Select("select * from task where task.taskId=#{taskId}")
    Task findTask(@Param("taskId") int taskId);

    /**
     * 属于班级ID的任务集合
     *
     * @param clazzId 班级ID
     * @return 任务集合
     */
    @Select("select * from task where task.taskPower=\"CLAZZ\" and task.taskPowerId=#{clazzId}")
    List<Task> findClazzAllTasks(@Param("clazzId") int clazzId);

    /**
     * 分页查询属于班级ID的任务集合
     *
     * @param clazzId 班级ID
     * @param start   开始条数
     * @param end     终止条数
     * @return 区间任务集合
     */
    @Select("select * from task where task.taskPower=\"CLAZZ\" and task.taskPowerId=#{clazzId} limit #{start},#{end}")
    List<Task> findClazzPagingTasks(@Param("clazzId") int clazzId, @Param("start") int start, @Param("end") int end);


    /**
     * 根据学院ID查找任务集合
     *
     * @param academyId 学院ID
     * @return 所属学院任务集合
     */
    @Select("select * from task where task.taskPower=\"ACADEMY\" and task.taskPowerId=#{academyId}")
    List<Task> findAcademyTasks(@Param("academyId") int academyId);

    /**
     * 分页查询属于学院ID的任务集合
     *
     * @param academyId 学院ID
     * @param start     开始条数
     * @param end       终止条数
     * @return 区间任务集合
     */
    @Select("select * from task where task.taskPower=\"ACADEMY\" and task.taskPowerId=#{academyId} limit #{start},#{end}")
    List<Task> findAcademyTasksPage(@Param("academyId") int academyId, @Param("start") int start, @Param("end") int end);


    /**
     * 插入任务
     *
     * @param task 插入任务的对象
     * @return 插入影响的数据行
     */
    @Insert("INSERT INTO `task`(`taskDateCreateId`, `taskCreateDate`, `taskStartDate`, `taskEndDate`, " +
            "`taskTitle`, `taskContent`, `taskPowerId`, " +
            "`taskPower`,`userId`) VALUES " +
            "(#{task.taskDateCreateId}, #{task.taskCreateDate}, #{task.taskStartDate}, #{task.taskEndDate}, " +
            "#{task.taskTitle}, #{task.taskContent}, #{task.taskPowerId}, " +
            "#{task.taskPower},#{task.userId})")
    int addTask(@Param("task") Task task);

    /**
     * 通过任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除条数
     */
    @Delete("DELETE FROM task WHERE `taskId` = #{taskId}")
    int deleteTask(@Param("taskId") int taskId);

    /**
     * 通过任务对象ID更新任务信息
     *
     * @param task 任务对象
     * @return 影响的条数
     */
    @Update("UPDATE task SET `taskStartDate` = #{task.taskStartDate}, `taskEndDate` = #{task.taskEndDate}, " +
            "`taskTitle` = #{task.taskTitle}, `taskContent` = #{task.taskContent}, " +
            "`lastChangeUserId`=#{task.lastChangeUserId} WHERE `taskId` = #{task.taskId}")
    int updateTask(@Param("task") Task task);

    /**
     * 查询所属学院任务总数
     *
     * @param academyId 学院ID
     * @return 任务总数
     */
    @Select("select count(*) from task where task.taskPower=\"ACADEMY\" and task.taskPowerId=#{academyId}")
    int getAcademyTaskCount(@Param("academyId") int academyId);

    /**
     * 查询所属班级任务总数
     *
     * @param clazzId 班级ID
     * @return 任务总数
     */
    @Select("select count(*) from task where task.taskPower=\"CLAZZ\" and task.taskPowerId=#{clazzId}")
    int getClazzTaskCount(@Param("clazzId") int clazzId);

    /**
     * 获取任务完成名单分页查询
     *
     * @param taskId 任务ID
     * @param start  开始项
     * @param end    结束项
     * @return 用户列表
     */
    List<User> getTaskCompleteRecordPage(@Param("taskId") int taskId, @Param("start") int start, @Param("end") int end);


    /**
     * 获取任务完成人数
     *
     * @param taskId 任务ID
     * @return 人数
     */
    @Select("select count(*) from taskrecord where taskrecord.taskId=#{taskId}")
    int getTaskCompleteUserCount(@Param("taskId") int taskId);

    /**
     * 班级已经结束任务总数
     *
     * @param clazzId 班级ID
     * @param nowDate 当前时间
     * @return 数量
     */
    @Select("select count(*) from task where task.taskPower= \"CLAZZ\" and task.taskPowerId = #{clazzId} and task.taskEndDate <= #{nowDate}")
    int getClazzCompleteTaskCount(@Param("clazzId") int clazzId, @Param("nowDate") Date nowDate);

    /**
     * 学院已经结束任务总数
     *
     * @param academyId 学院ID
     * @param nowDate   当前时间
     * @return 数量
     */
    @Select("select count(*) from task where task.taskPower= \"ACADEMY\" and task.taskPowerId=#{academyId} and task.taskEndDate <= #{nowDate}")
    int getAcademyCompleteTaskCount(@Param("academyId") int academyId, @Param("nowDate") Date nowDate);


    /**
     * 班级正在进行任务总数
     *
     * @param clazzId 班级ID
     * @param nowDate 当前时间
     * @return 数量
     */
    @Select("select count(*) from task where task.taskPower= \"CLAZZ\" and task.taskPowerId = #{clazzId} and task.taskEndDate >= #{nowDate} and task.taskStartDate <= #{nowDate}")
    int getClazzStartingTaskCount(@Param("clazzId") int clazzId, @Param("nowDate") Date nowDate);

    /**
     * 学院正在进行任务总数
     *
     * @param academyId 学院ID
     * @param nowDate   当前时间
     * @return 数量
     */
    @Select("select count(*) from task where task.taskPower= \"ACADEMY\" and task.taskPowerId=#{academyId} and task.taskEndDate >= #{nowDate} and task.taskStartDate <= #{nowDate}")
    int getAcademyStartingTaskCount(@Param("academyId") int academyId, @Param("nowDate") Date nowDate);


}
