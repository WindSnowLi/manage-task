package com.windsnowli.workserver.services.inter;

import com.windsnowli.workserver.entity.Student;
import com.windsnowli.workserver.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author windSnowLi
 */
public interface StudentServiceInter {

    /**
     * 查找用户班级
     *
     * @param user 用户对象
     * @return 所在班级ID
     */
    int findClazzId(User user);

    /**
     * 查找用户班级
     *
     * @param userId 用户对象ID
     * @return 所在班级ID
     */
    int findClazzId(int userId);

    /**
     * 获取学生本身的基础信息
     *
     * @param userId 用户Id
     * @return 学生对象
     */
    Student findStudentBaseData(int userId);


    /**
     * 设置用户学生身份基础信息
     *
     * @param user 用户对象
     * @return 用户对象本身
     */
    Student setStudentBaseData(User user);

    /**
     * 获取班级人数数量
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    int getClazzUserCount(int clazzId);


    /**
     * 获取班级人数数量
     *
     * @param clazzId 班级ID
     * @return 班级人数Msg信息
     */
    String getClazzUserCountJson(int clazzId);

    /**
     * 获取学院人数数量
     *
     * @param academyId 学院ID
     * @return 学院人数
     */
    int getAcademyUserCount(int academyId);


    /**
     * 获取学院人数数量
     *
     * @param academyId 学院ID
     * @return 学院人数Msg信息
     */
    String getAcademyUserCountJson(int academyId);
}
