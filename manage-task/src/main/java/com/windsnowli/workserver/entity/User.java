package com.windsnowli.workserver.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author windSnowLi
 */
@Getter
@Setter
@ToString
public class User implements Serializable {

    public enum UserIdentity {
        /**
         * 普通学生身份
         */
        STUDENT,
        /**
         * 学生班级任务管理权
         */
        STUDENT_CLAZZ;
    }

    protected int userId;
    protected String userNumber;
    protected String userName;
    protected String userEmail;

    @JSONField(serialize = false)
    protected String userPasswordSalt;
    @JSONField(serialize = false)
    protected String userPassword;

    protected int userPower;
    protected String userNickname;
    protected String userHeadPicture;
    protected String userCreateTime;
    protected UserIdentity userIdentity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId && userNumber.equals(user.userNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userNumber);
    }
}
