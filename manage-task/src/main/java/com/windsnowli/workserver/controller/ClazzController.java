package com.windsnowli.workserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.windsnowli.workserver.annotation.PowerLevel;
import com.windsnowli.workserver.services.impl.ClazzServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author windSnowLi
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    private ClazzServiceImpl clazzService;

    @Autowired
    public void setClazzService(ClazzServiceImpl clazzService) {
        this.clazzService = clazzService;
    }

    /**
     * 获取班级人数
     *
     * @param clazzJson 班级ID,格式{"clazzId":"int"}
     * @return 班级人数Msg信息
     */
    @RequestMapping("getClazzUserCountJson")
    public String getClazzUserCountJson(@RequestBody JSONObject clazzJson) {
        return clazzService.getClazzUserCountJson(clazzJson.getInteger("clazzId"));
    }
}
