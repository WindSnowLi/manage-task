package com.windsnowli.workserver.services.impl;

import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.Task;
import com.windsnowli.workserver.entity.TaskRecord;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.mapper.TaskMapper;
import com.windsnowli.workserver.mapper.TaskRecordMapper;
import com.windsnowli.workserver.services.inter.TaskServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author windSnowLi
 */
@Service("taskService")
public class TaskServiceImpl implements TaskServiceInter {

    private TaskMapper taskMapper;
    private StudentServiceImpl studentService;
    private TaskRecordMapper taskRecordMapper;

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setTaskRecordMapper(TaskRecordMapper taskRecordMapper) {
        this.taskRecordMapper = taskRecordMapper;
    }

    /**
     * 通过ID查找任务
     *
     * @param taskId 任务ID
     * @return 返回任务对象
     */
    @Override
    public Task findTask(int taskId) {
        return taskMapper.findTask(taskId);
    }

    /**
     * 通过包含任务ID的对象查找任务
     *
     * @param task 办好任务ID的任务对象
     * @return 返回任务对象
     */
    @Override
    public Task findTask(Task task) {
        return this.findTask(task.getTaskId());
    }

    /**
     * 通过ID查找任务
     *
     * @param taskId 任务ID
     * @return 返回任务json格式的Msg信息
     */
    @Override
    public String findTaskJson(int taskId) {
        Task tempTask = this.findTask(taskId);
        if (null == tempTask) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
        } else {
            return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, tempTask);
        }

    }

    /**
     * 通过ID查找任务
     *
     * @param task 任务对象
     * @return 返回任务json格式的Msg信息
     */
    @Override
    public String findTaskJson(Task task) {
        return this.findTaskJson(task.getTaskId());
    }


    /**
     * 返回属于班级ID的任务集合
     *
     * @param clazzId 班级ID
     * @return 任务集合
     */
    @Override
    public List<Task> findClazzTasks(int clazzId) {
        return taskMapper.findClazzAllTasks(clazzId);
    }

    /**
     * 通过班级ID查询所属任务
     *
     * @param clazzId 班级ID
     * @return 返回属于班级ID的任务集合Msg信息
     */
    @Override
    public String findClazzTasksJson(int clazzId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.findClazzTasks(clazzId));
    }

    /**
     * 分页查询属于班级ID的任务集合
     *
     * @param clazzId     班级ID
     * @param currentPage 当前页
     * @param pageSize    页内容数量
     * @return 任务集合
     */
    @Override
    public List<Task> findClazzTasks(int clazzId, int currentPage, int pageSize) {
        List<Task> taskList = taskMapper.findClazzPagingTasks(clazzId, (currentPage - 1) * pageSize, currentPage * pageSize);
        for (Task task : taskList) {
            task.setCompleteRate(this.getTaskCompleteRate(task.getTaskId()));
        }
        return taskList;
    }

    /**
     * 通过班级ID分页查询属于班级ID任务集合Msg信息
     *
     * @param clazzId     班级ID
     * @param currentPage 当前页
     * @param pageSize    页内容数量
     * @return 返回分页查询属于班级ID的任务集合Msg信息
     */
    @Override
    public String findClazzTasksJson(int clazzId, int currentPage, int pageSize) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.findClazzTasks(clazzId, currentPage, pageSize));
    }

    /**
     * 根据学院ID查找任务集合
     *
     * @param academyId 学院ID
     * @return 所属学院任务集合
     */
    @Override
    public List<Task> findAcademyTasks(int academyId) {
        return taskMapper.findAcademyTasks(academyId);
    }

    /**
     * 通过学院ID分页查询属于学院ID任务集合
     *
     * @param academyId   学院ID
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 返回分页查询属于学院ID的任务集合
     */
    @Override
    public List<Task> findAcademyTasks(int academyId, int currentPage, int pageSize) {
        return taskMapper.findAcademyTasksPage(academyId, (currentPage - 1) * pageSize, currentPage * pageSize);
    }

    /**
     * 通过学院ID分页查询属于学院ID任务集合Msg信息
     *
     * @param academyId   学院ID
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 返回分页查询属于学院ID的任务集合Msg信息
     */
    @Override
    public String findAcademyTasksJson(int academyId, int currentPage, int pageSize) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.findAcademyTasks(academyId, currentPage, pageSize));
    }

    /**
     * 根据学院ID查找任务集合Msg信息
     *
     * @param academyId 学院ID
     * @return 所属学院任务集合Msg信息
     */
    @Override
    public String findAcademyTasksJson(int academyId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.findAcademyTasks(academyId));
    }


    /**
     * 插入任务
     *
     * @param task 插入任务的对象
     * @return 插入结果，Msg信息
     */
    @Override
    public String addTaskJson(Task task) {
        task.setTaskDateCreateId(Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date())));
        task.setTaskCreateDate(new Date());
        if (this.addTask(task) > 0) {
            return Msg.getSuccessMsg();
        } else {
            return Msg.getFailMsg();
        }
    }

    /**
     * 插入任务
     *
     * @param task 插入任务的对象
     * @return 插入结果为插入影响的行数
     */
    @Override
    public int addTask(Task task) {
        return taskMapper.addTask(task);
    }

    /**
     * 通过任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除结果，Msg信息
     */
    @Override
    public String deleteTaskJson(int taskId) {
        return this.deleteTask(taskId) == 1 ? Msg.getSuccessMsg() : Msg.getFailMsg();
    }

    /**
     * 通过任务ID删除任务
     *
     * @param task 任务对象
     * @return 删除结果，Msg信息
     */
    @Override
    public String deleteTaskJson(Task task) {
        return this.deleteTaskJson(task.getTaskId());
    }

    /**
     * 通过任务ID删除任务
     *
     * @param taskId 任务ID
     * @return 删除条数
     */
    @Override
    public int deleteTask(int taskId) {
        return taskMapper.deleteTask(taskId);
    }

    /**
     * 通过任务对象删除任务
     *
     * @param task 任务对象
     * @return 删除条数
     */
    @Override
    public int deleteTask(Task task) {
        return this.deleteTask(task.getTaskId());
    }

    /**
     * 通过任务对象ID更新任务信息
     *
     * @param task 任务对象
     * @return 影响的条数
     */
    @Override
    public int updateTask(Task task) {
        return taskMapper.updateTask(task);
    }

    /**
     * 通过任务对象ID更新任务信息
     *
     * @param task 任务对象
     * @return 修改结果Msg信息
     */
    @Override
    public String updateTaskJson(Task task) {
        return taskMapper.updateTask(task) == 1 ? Msg.getSuccessMsg() : Msg.getFailMsg();
    }

    /**
     * 查询所属学院任务总数
     *
     * @param academyId 学院ID
     * @return 任务总数
     */
    @Override
    public String getAcademyTaskCount(int academyId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, taskMapper.getAcademyTaskCount(academyId));
    }

    /**
     * 查询所属班级任务总数
     *
     * @param clazzId 班级ID
     * @return 任务总数
     */
    @Override
    public String getClazzTaskCount(int clazzId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, taskMapper.getClazzTaskCount(clazzId));
    }

    /**
     * 获取任务完成名单分页查询
     *
     * @param taskId      任务ID
     * @param currentPage 开始项
     * @param pageSize    结束项
     * @return 用户列表
     */
    @Override
    public List<User> getTaskCompleteRecordPage(int taskId, int currentPage, int pageSize) {
        return taskMapper.getTaskCompleteRecordPage(taskId, (currentPage - 1) * pageSize, currentPage * pageSize);
    }

    /**
     * 获取任务完成名单分页查询
     *
     * @param taskId      任务ID
     * @param currentPage 开始项
     * @param pageSize    结束项
     * @return 用户列表
     */
    @Override
    public String getTaskCompleteRecordPageJson(int taskId, int currentPage, int pageSize) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getTaskCompleteRecordPage(taskId, currentPage, pageSize));
    }

    /**
     * 获取任务完成人数
     *
     * @param taskId 任务ID
     * @return 人数
     */
    @Override
    public int getTaskCompleteUserCount(int taskId) {
        return taskMapper.getTaskCompleteUserCount(taskId);
    }

    /**
     * 获取任务完成人数
     *
     * @param taskId 任务ID
     * @return 人数Msg信息
     */
    @Override
    public String getTaskCompleteUserCountJson(int taskId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getTaskCompleteUserCount(taskId));
    }

    /**
     * 任务完成度
     *
     * @param taskId 任务ID
     * @return 任务完成度，单位百分比
     */
    @Override
    public double getTaskCompleteRate(int taskId) {
        Task tempTask = this.findTask(taskId);
        if (null == tempTask) {
            return 0;
        }
        double rate;
        switch (tempTask.getTaskPower()) {
            case CLAZZ:
                rate = this.getTaskCompleteUserCount(taskId) * 1.0 / studentService.getClazzUserCount(tempTask.getTaskPowerId()) * 100;
                break;
            case ACADEMY:
                rate = this.getTaskCompleteUserCount(taskId) * 1.0 / studentService.getAcademyUserCount(tempTask.getTaskPowerId()) * 100;
                break;
            default:
                rate = 0;
        }
        return rate;
    }

    /**
     * 任务完成度
     *
     * @param taskId 任务ID
     * @return 任务完成度，单位百分比Msg信息
     */
    @Override
    public String getTaskCompleteRateJson(int taskId) {
        return Msg.getSuccessMsg(this.getTaskCompleteRate(taskId));
    }

    /**
     * 获取任务未完成人数
     *
     * @param taskId 任务ID
     * @return 人数
     */
    @Override
    public int getTaskUnfinishedCount(int taskId) {
        Task tempTask = taskMapper.findTask(taskId);
        if (tempTask == null) {
            return 0;
        }
        switch (tempTask.getTaskPower()) {
            case CLAZZ:
                return studentService.getClazzUserCount(tempTask.getTaskPowerId()) - getTaskCompleteUserCount(taskId);
            case ACADEMY:
                return studentService.getAcademyUserCount(tempTask.getTaskPowerId()) - getTaskCompleteUserCount(taskId);
            default:
                return 0;
        }
    }

    /**
     * 获取任务未完成人数
     *
     * @param taskId 任务ID
     * @return 人数Msg信息
     */
    @Override
    public String getTaskUnfinishedCountJson(int taskId) {
        return Msg.getSuccessMsg(this.getTaskUnfinishedCount(taskId));
    }

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 影响条数
     */
    @Override
    public int completeTask(int taskId, int userId) {
        if (1 != taskRecordMapper.selectCompleteTaskRecord(taskId, userId)) {
            return taskRecordMapper.completeTask(taskId, userId, new Date());
        } else {
            return taskRecordMapper.updateCompleteTask(taskId, userId, new Date());
        }
    }

    /**
     * 班级已经结束任务总数
     *
     * @param clazzId 班级ID
     * @return 数量
     */
    @Override
    public int getClazzCompleteTaskCount(int clazzId) {
        return taskMapper.getClazzCompleteTaskCount(clazzId, new Date());
    }

    /**
     * 学院已经结束任务总数
     *
     * @param academyId 学院ID
     * @return 数量
     */
    @Override
    public int getAcademyCompleteTaskCount(int academyId) {
        return taskMapper.getAcademyCompleteTaskCount(academyId, new Date());
    }

    /**
     * 班级已经结束任务总数
     *
     * @param clazzId 班级ID
     * @return 数量Msg信息
     */
    @Override
    public String getClazzCompleteTaskCountJson(int clazzId) {
        return Msg.getSuccessMsg(this.getClazzCompleteTaskCount(clazzId));
    }

    /**
     * 学院已经结束任务总数
     *
     * @param academyId 学院ID
     * @return 数量Msg信息
     */
    @Override
    public String getAcademyCompleteTaskCountJson(int academyId) {
        return this.getAcademyTaskCount(academyId);
    }

    /**
     * 班级正在进行任务总数
     *
     * @param clazzId 班级ID
     * @return 数量
     */
    @Override
    public int getClazzStartingTaskCount(int clazzId) {
        return taskMapper.getClazzStartingTaskCount(clazzId, new Date());
    }

    /**
     * 学院正在进行任务总数
     *
     * @param academyId 学院ID
     * @return 数量
     */
    @Override
    public int getAcademyStartingTaskCount(int academyId) {
        return taskMapper.getAcademyStartingTaskCount(academyId, new Date());
    }

    /**
     * 班级正在进行任务总数
     *
     * @param clazzId 班级ID
     * @return 数量Msg信息
     */
    @Override
    public String getClazzStartingTaskCountJson(int clazzId) {
        return Msg.getSuccessMsg(this.getClazzStartingTaskCount(clazzId));
    }

    /**
     * 学院正在进行任务总数
     *
     * @param academyId 学院ID
     * @return 数量Msg信息
     */
    @Override
    public String getAcademyStartingTaskCountJson(int academyId) {
        return Msg.getSuccessMsg(this.getAcademyStartingTaskCount(academyId));
    }

    /**
     * 查询用户完成报告信息
     *
     * @param taskId 任务ID
     * @param userId 用户ID
     * @return 任务完成报告对象
     */
    @Override
    public TaskRecord findTaskRecord(int taskId, int userId) {
        return taskRecordMapper.findTaskRecord(taskId, userId);
    }
}
