package com.windsnowli.workserver.mapper;

import com.windsnowli.workserver.entity.TaskRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author windSnowLi
 */
@Mapper
@Repository
public interface TaskRecordMapper {
    /**
     * 完成任务
     *
     * @param taskId         任务ID
     * @param userId         用户ID
     * @param submissionTime 提交时间
     * @return 插入条数
     */
    @Insert("INSERT INTO taskrecord (taskId,userId,submissionTime) VALUES (#{taskId},#{userId},#{submissionTime})")
    int completeTask(@Param("taskId") int taskId, @Param("userId") int userId, @Param("submissionTime") Date submissionTime);

    /**
     * 更新任务完成时间
     *
     * @param taskId         任务ID
     * @param userId         用户ID
     * @param submissionTime 提交时间
     * @return 更新影响条数
     */
    @Update("UPDATE taskrecord SET `submissionTime`=#{submissionTime} WHERE `taskId` = #{task.taskId} and userId=#{userId}")
    int updateCompleteTask(@Param("taskId") int taskId, @Param("userId") int userId, @Param("submissionTime") Date submissionTime);

    /**
     * 任务是否存在
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 是否存在
     */
    @Select("select 1 from taskrecord where taskId=#{taskId} and userId=#{userId} limit 1")
    int selectCompleteTaskRecord(@Param("taskId") int taskId, @Param("userId") int userId);

    /**
     * 查询用户完成报告信息
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务完成报告对象
     */
    TaskRecord findTaskRecord(@Param("taskId") int taskId, @Param("userId") int userId);
}
