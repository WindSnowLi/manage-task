package com.windsnowli.workserver.mapper;

import com.windsnowli.workserver.entity.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author windSnowLi
 */
@Mapper
@Repository
public interface ClazzMapper {
    /**
     * 根据班级ID查找班级
     *
     * @param clazzId 班级ID
     * @return 班级对象
     */
    @Select("select * from clazz where clazz.clazzId=#{clazzId}")
    Clazz findClazz(@Param("clazzId") int clazzId);


    /**
     * 获取班级人数
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    @Select("select count(*) from clazz where clazz.clazzId=#{clazzId}")
    int getClazzUserCount(@Param("clazzId") int clazzId);
}
