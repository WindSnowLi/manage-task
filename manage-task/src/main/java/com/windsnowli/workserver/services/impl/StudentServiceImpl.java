package com.windsnowli.workserver.services.impl;

import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.Student;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.mapper.StudentMapper;
import com.windsnowli.workserver.services.inter.StudentServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author windSnowLi
 */
@Service("studentService")
public class StudentServiceImpl implements StudentServiceInter {

    private StudentMapper studentMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * 查找用户班级
     *
     * @param user 用户对象
     * @return 所在班级ID
     */
    @Override
    public int findClazzId(User user) {
        return this.findStudentBaseData(user.getUserId()).getStudentClazzId();
    }

    /**
     * 查找用户班级
     *
     * @param userId 用户对象ID
     * @return 所在班级ID
     */
    @Override
    public int findClazzId(int userId) {
        return this.findStudentBaseData(userId).getStudentClazzId();
    }

    /**
     * 获取学生本身的基础信息
     *
     * @param userId 用户Id
     * @return 学生对象
     */
    @Override
    public Student findStudentBaseData(int userId) {
        return studentMapper.findStudentBaseData(userId);
    }

    /**
     * 设置用户学生身份基础信息
     *
     * @param user 用户对象
     * @return 用户对象本身
     */
    @Override
    public Student setStudentBaseData(User user) {
        return this.findStudentBaseData(user.getUserId()).setUserBaseData(user);
    }

    /**
     * 获取班级人数数量
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    @Override
    public int getClazzUserCount(int clazzId) {
        return studentMapper.getClazzUserCount(clazzId);
    }

    /**
     * 获取班级人数数量
     *
     * @param clazzId 班级ID
     * @return 班级人数Msg信息
     */
    @Override
    public String getClazzUserCountJson(int clazzId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getClazzUserCount(clazzId));
    }

    /**
     * 获取学院人数数量
     *
     * @param academyId 学院ID
     * @return 学院人数
     */
    @Override
    public int getAcademyUserCount(int academyId) {
        return studentMapper.getAcademyUserCount(academyId);
    }

    /**
     * 获取学院人数数量
     *
     * @param academyId 学院ID
     * @return 学院人数Msg信息
     */
    @Override
    public String getAcademyUserCountJson(int academyId) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.getAcademyUserCount(academyId));
    }


}
