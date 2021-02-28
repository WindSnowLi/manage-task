package com.windsnowli.workserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

/**
 * @author windSnowLi
 */
@Getter
@Setter
@ToString
public class Task {
    public enum Task_power {
        //班级级别
        CLAZZ("CLAZZ"),
        //学院级别
        ACADEMY("ACADEMY"),
        //专业级别
        MAJOR("MAJOR");
        private final String ext;

        Task_power(String ext) {
            this.ext = ext;
        }

        public String getExt() {
            return ext;
        }
    }

    protected int taskId;
    protected int taskDateCreateId;
    protected Date taskCreateDate;
    protected Date taskStartDate;
    protected Date taskEndDate;
    protected String taskTitle;
    protected String taskContent;
    protected int taskPowerId;
    protected Task_power taskPower;
    protected double completeRate;
    /**
     * 发起人Id
     */
    protected int userId;
    /**
     * 最后修改人ID
     */
    protected int lastChangeUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
