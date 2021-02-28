package com.windsnowli.workserver.mapper;

import com.windsnowli.workserver.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author windSnowLi
 */
@Mapper
@Repository
public interface StudentMapper {
    /**
     * 获取学生本身的基础信息
     *
     * @param userId 用户Id
     * @return 学生对象
     */
    @Select("select * from student where student.userId=#{userId}")
    Student findStudentBaseData(@Param("userId") int userId);


    /**
     * 获取班级人数数量
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    @Select("select count(*) from student where student.studentClazzId=#{clazzId}")
    int getClazzUserCount(@Param("clazzId") int clazzId);


    /**
     * 获取学院人数数量
     *
     * @param academyId 学院ID
     * @return 学院人数
     */
    @Select("select count(*) from student where student.studentAcademyId=#{academyId}")
    int getAcademyUserCount(@Param("academyId") int academyId);
}
