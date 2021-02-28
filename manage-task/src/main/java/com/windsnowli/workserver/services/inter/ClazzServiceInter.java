package com.windsnowli.workserver.services.inter;

import com.windsnowli.workserver.entity.Clazz;
import org.springframework.stereotype.Service;

/**
 * @author windSnowLi
 */
public interface ClazzServiceInter {
    /**
     * 查找班级
     *
     * @param clazzId 班级ID
     * @return 班级对象
     */
    Clazz findClazz(int clazzId);

    /**
     * 获取班级人数
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    int getClazzUserCount(int clazzId);

    /**
     * 获取班级人数
     *
     * @param clazzId 班级ID
     * @return 班级人数Msg信息
     */
    String getClazzUserCountJson(int clazzId);
}
