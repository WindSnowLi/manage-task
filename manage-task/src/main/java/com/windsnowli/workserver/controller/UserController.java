package com.windsnowli.workserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.windsnowli.workserver.annotation.PassToken;
import com.windsnowli.workserver.annotation.UserLoginToken;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.services.impl.UserServiceImpl;
import com.windsnowli.workserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author windSnowLi
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
@UserLoginToken
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    private void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 登录请求
     *
     * @param userJson 用户信息json串，包含账户与密码
     * @return 如果验证正确返回信息串，验证错误返回空
     */
    @PostMapping(value = "login")
    @UserLoginToken(required = false)
    @PassToken
    public String login(@RequestBody JSONObject userJson) {
        return userService.loginJson(JSONObject.parseObject(userJson.toJSONString(), User.class));
    }

    /**
     * 更新用户信息请求
     *
     * @param userJson 用户信息json串，包含基础信息
     * @return 更新结果Msg信息, 附带新信息
     */
    @PostMapping(value = "updateUserBase")
    public String updateUserBaseJson(@RequestBody JSONObject userJson) {
        return userService.updateUserBaseJson(JSONObject.parseObject(userJson.toJSONString(), User.class));
    }

    /**
     * 通过用户ID查找用户
     *
     * @param userJson 用户ID
     * @return 用户对象Msg信息
     */
    @PostMapping(value = "findIdUserJson")
    public String findIdUserJson(@RequestBody JSONObject userJson) {
        return userService.findIdUserJson(userJson.getInteger("userId"));
    }

    /**
     * 更新密码
     *
     * @param userJson 用户信息，格式{"token":"String","oldPassword":"String","newPassword":"String"}
     * @return 更新状态Msg信息
     */
    @PostMapping(value = "updatePasswordJson")
    public String updatePasswordJson(@RequestBody JSONObject userJson) {
        return userService.updatePasswordJson(JwtUtils.getTokenUserId(userJson.getString("token")), userJson.getString("oldPassword"), userJson.getString("newPassword"));
    }

    /**
     * 重置密码
     *
     * @param userJson 账号，格式{"userNumber":"String"}
     * @return 重置状态Msg信息
     */
    @UserLoginToken(required = false)
    @PassToken
    @PostMapping(value = "resetPasswordJson")
    public String resetPasswordJson(@RequestBody JSONObject userJson) {
        return userService.resetPasswordJson(userJson.getString("userNumber"));
    }
}
