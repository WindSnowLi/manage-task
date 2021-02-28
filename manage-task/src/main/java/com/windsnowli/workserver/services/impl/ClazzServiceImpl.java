package com.windsnowli.workserver.services.impl;

import com.windsnowli.workserver.entity.Clazz;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.mapper.ClazzMapper;
import com.windsnowli.workserver.services.inter.ClazzServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author windSnowLi
 */
@Service("clazzService")
public class ClazzServiceImpl implements ClazzServiceInter {

    private ClazzMapper clazzMapper;

    @Autowired
    public void setClazzMapper(ClazzMapper clazzMapper) {
        this.clazzMapper = clazzMapper;
    }

    /**
     * 查找班级
     *
     * @param clazzId 班级ID
     * @return 班级对象
     */
    @Override
    public Clazz findClazz(int clazzId) {
        return clazzMapper.findClazz(clazzId);
    }

    /**
     * 获取班级人数
     *
     * @param clazzId 班级ID
     * @return 班级人数
     */
    @Override
    public int getClazzUserCount(int clazzId) {
        return clazzMapper.getClazzUserCount(clazzId);
    }

    /**
     * 获取班级人数
     *
     * @param clazzId 班级ID
     * @return 班级人数Msg信息
     */
    @Override
    public String getClazzUserCountJson(int clazzId) {
        return Msg.getSuccessMsg(this.getClazzUserCount(clazzId));
    }
}
