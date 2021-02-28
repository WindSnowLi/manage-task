package com.windsnowli.workserver.mapper;

import com.windsnowli.workserver.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author windSnowLi
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 使用账户密码验证登录
     *
     * @param number   账户
     * @param password 密码
     * @return 返回对象json串
     */
    @Select("select * from user where user.userNumber=#{number} and " +
            "user.userPassword=#{password}")
    User login(@Param("number") String number, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 删除条数
     */
    @Delete("delete from user where user.userId=#{userId}")
    int deleteUser(@Param("userId") int userId);

    /**
     * 通过用户账号查找用户信息
     *
     * @param number 用户账号
     * @return 用户对象
     */
    @Select("select * from user where user.userNumber=#{number}")
    User findNumberUser(@Param("number") String number);

    /**
     * 根据用户对象更新用户信息
     *
     * @param user 用户对象
     * @return 修改影响条数
     */
    @Update("UPDATE `user` SET `userEmail` = #{user.userEmail}, `userNickname` = #{user.userNickname} WHERE `userId` = #{user.userId}")
    int updateUserBase(@Param("user") User user);


    /**
     * 通过用户ID查找用户
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    @Select("select * from user where userId=#{userId}")
    User findIdUser(@Param("userId") int userId);


    /**
     * 修改用户密码
     *
     * @param userId       用户ID
     * @param password     新密码+盐SHA512
     * @param passwordSalt 盐
     * @return 修改影响条数
     */
    @Update("UPDATE `user` SET userPasswordSalt=#{passwordSalt},userPassword=#{password} WHERE userId=#{userId}")
    int updatePassword(@Param("userId") int userId, @Param("password") String password, @Param("passwordSalt") String passwordSalt);
}
