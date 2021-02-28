package com.windsnowli.workserver.services.inter;

import com.windsnowli.workserver.annotation.PassToken;
import com.windsnowli.workserver.entity.User;
import org.springframework.stereotype.Service;


/**
 * @author windSnowLi
 */
public interface UserServiceInter {
    /**
     * 使用对象验证
     *
     * @param user 用户对象，包含账户与密码
     * @return 查找结果转化为Msg信息
     */
    String loginJson(User user);

    /**
     * 使用账户密码验证
     *
     * @param number   账户
     * @param password 密码
     * @return 查找结果转化为Msg信息
     */
    String loginJson(String number, String password);

    /**
     * 通过用户账号查找用户对象
     *
     * @param number 用户账号
     * @return 用户对象
     */
    User findNumberUser(String number);

    /**
     * 通过用户账号查找用户对象
     *
     * @param number 用户账号
     * @return 用户对象, Msg信息
     */
    String findNumberUserJson(String number);


    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 删除结果Json
     */
    String deleteUserJson(int userId);

    /**
     * 通过用户对象删除用户
     *
     * @param user 用户对象
     * @return 删除结果Json
     */
    String deleteUserJson(User user);


    /**
     * 根据用户对象更新用户信息
     *
     * @param user 用户对象
     * @return 修改影响条数
     */
    int updateUserBase(User user);

    /**
     * 根据用户对象更新用户信息
     *
     * @param user 用户对象
     * @return 更新结果Msg信息
     */
    String updateUserBaseJson(User user);


    /**
     * 通过用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    User findIdUser(int userId);

    /**
     * 通过用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户对象Msg信息
     */
    String findIdUserJson(int userId);


    /**
     * 修改用户密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newString   新密码
     * @return 修改状态Msg
     */
    String updatePasswordJson(int userId, String oldPassword, String newString);

    /**
     * 修改用户密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newString   新密码
     * @return 修改状态
     */
    int updatePassword(int userId, String oldPassword, String newString);


    /**
     * 重置密码
     *
     * @param userNumber 账号
     * @return 重置状态Msg信息
     */
    String resetPasswordJson(String userNumber);
}
