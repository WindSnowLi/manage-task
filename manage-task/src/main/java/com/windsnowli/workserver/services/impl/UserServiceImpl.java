package com.windsnowli.workserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.windsnowli.workserver.entity.Msg;
import com.windsnowli.workserver.entity.User;
import com.windsnowli.workserver.mapper.UserMapper;
import com.windsnowli.workserver.services.inter.MailServiceInter;
import com.windsnowli.workserver.services.inter.UserServiceInter;
import com.windsnowli.workserver.utils.CodeUtils;
import com.windsnowli.workserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

/**
 * @author windSnowLi
 */
@Service("userService")
public class UserServiceImpl implements UserServiceInter {

    private UserMapper userMapper;

    private StudentServiceImpl studentService;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    private MailServiceInter mailServiceInter;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    /**
     * 使用对象验证
     *
     * @param user 用户对象，包含账户与密码
     * @return 查找结果转化为Msg信息
     */
    @Override
    public String loginJson(User user) {
        return this.loginJson(user.getUserNumber(), user.getUserPassword());
    }

    /**
     * 使用账户密码验证
     *
     * @param number   账户
     * @param password 密码
     * @return 查找结果转化为Msg信息
     */
    @Override
    public String loginJson(String number, String password) {
        User user = this.findNumberUser(number);
        if (user == null) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.LOGIN_NUMBER_FAIL, null);
        }
        //比较密码
        if (CodeUtils.getSha512ToMd5(password, user.getUserPasswordSalt()).equals(user.getUserPassword())) {
            if (user.getUserIdentity() == User.UserIdentity.STUDENT ||
                    user.getUserIdentity() == User.UserIdentity.STUDENT_CLAZZ) {
                user = studentService.findStudentBaseData(user.getUserId()).setUserBaseData(user);
            }
            JSONObject tempJson = (JSONObject) JSONObject.toJSON(user);
            tempJson.put("token", JwtUtils.getToken(user));
            return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, tempJson);
        } else {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.LOGIN_PASSWORD_FAIL, null);
        }
    }

    /**
     * 通过用户账号查找用户对象
     *
     * @param number 用户账号
     * @return 用户对象
     */
    @Override
    public User findNumberUser(String number) {
        return userMapper.findNumberUser(number);
    }

    /**
     * 通过用户账号查找用户对象
     *
     * @param number 用户账号
     * @return 用户对象, Msg信息
     */
    @Override
    public String findNumberUserJson(String number) {
        User tempUser = this.findNumberUser(number);
        if (tempUser == null) {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.LOGIN_NUMBER_FAIL, null);
        } else if (tempUser.getUserPower() == 0) {
            tempUser = studentService.setStudentBaseData(tempUser);
        }
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, tempUser);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 返回删除结果, Msg信息
     */
    @Override
    public String deleteUserJson(int userId) {
        //删除影响的条数
        if (userMapper.deleteUser(userId) > 0) {
            return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
        } else {
            return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
        }
    }

    /**
     * 通过用户对象删除用户
     *
     * @param user 用户对象
     * @return 返回删除结果, Msg信息
     */
    @Override
    public String deleteUserJson(User user) {
        return this.deleteUserJson(user.getUserId());
    }

    /**
     * 根据用户对象更新用户信息
     *
     * @param user 用户对象
     * @return 修改影响条数
     */
    @Override
    public int updateUserBase(User user) {
        return userMapper.updateUserBase(user);
    }

    /**
     * 根据用户对象更新用户信息
     *
     * @param user 用户对象
     * @return 更新结果Msg信息
     */
    @Override
    public String updateUserBaseJson(User user) {
        return this.updateUserBase(user) > 0 ? Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, this.findIdUser(user.getUserId())) : Msg.getFailMsg();
    }

    /**
     * 通过用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public User findIdUser(int userId) {
        return userMapper.findIdUser(userId);
    }


    /**
     * 通过用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户对象Msg信息
     */
    @Override
    public String findIdUserJson(int userId) {
        return Msg.getSuccessMsg(userMapper.findIdUser(userId));
    }

    /**
     * 修改用户密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改状态Msg
     */
    @Override
    public String updatePasswordJson(int userId, String oldPassword, String newPassword) {

        User user = this.findIdUser(userId);
        if (null == user) {
            return Msg.getFailMsg();
        }

        if (!(user.getUserPassword().equals(CodeUtils.getSha512ToMd5(oldPassword, user.getUserPasswordSalt())))) {
            return Msg.getFailMsg();
        }
        user.setUserPasswordSalt(CodeUtils.getRandomPassword());
        return this.updatePassword(userId, CodeUtils.getSha512ToMd5(newPassword, user.getUserPasswordSalt()), user.getUserPasswordSalt()) == 1 ? Msg.getSuccessMsg() : Msg.getFailMsg();
    }

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @param salt     盐
     * @return 修改状态
     */
    @Override
    public int updatePassword(int userId, String password, String salt) {
        return userMapper.updatePassword(userId, password, salt);

    }


    /**
     * 重置密码
     *
     * @param userNumber 账号
     * @return 重置状态Msg信息
     */
    @Override
    public String resetPasswordJson(String userNumber) {
        User user = userMapper.findNumberUser(userNumber);
        if (null == user) {
            return Msg.getFailMsg();
        }
        //密码与盐相同
        user.setUserPasswordSalt(CodeUtils.getRandomPassword());
        user.setUserPassword(CodeUtils.getSha512ToMd5(user.getUserPasswordSalt(), user.getUserPasswordSalt()));
        if (this.updatePassword(user.getUserId(), user.getUserPassword(), user.getUserPasswordSalt()) == 1) {
            Context context = new Context();
            context.setVariable("password", user.getUserPasswordSalt());
            String emailContent = templateEngine.process("mail/informPassword", context);
            mailServiceInter.sendHtmlMail(user.getUserEmail(), "密码重置通知", emailContent);
            return Msg.getSuccessMsg();
        }
        return Msg.getFailMsg();
    }
}
