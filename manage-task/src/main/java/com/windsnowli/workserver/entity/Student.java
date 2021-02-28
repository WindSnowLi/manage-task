package com.windsnowli.workserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author windSnowLi
 */
@Setter
@Getter
@ToString
public class Student extends User implements Serializable {
    protected int studentClazzId;

    public Student() {
    }

    protected int studentAcademyId;
    protected int studentMajorId;

    public Student(User user) {
        this.setUserBaseData(user);
    }

    public Student setUserBaseData(User user) {
        this.userId = user.userId;
        this.userNumber = user.userNumber;
        this.userName = user.userName;
        this.userEmail = user.userEmail;
        this.userPasswordSalt = user.userPasswordSalt;
        this.userPassword = user.userPassword;
        this.userPower = user.userPower;
        this.userNickname = user.userNickname;
        this.userHeadPicture = user.userHeadPicture;
        this.userCreateTime = user.userCreateTime;
        this.userIdentity = user.userIdentity;
        return this;
    }
}
