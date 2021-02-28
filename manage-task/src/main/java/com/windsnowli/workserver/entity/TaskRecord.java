package com.windsnowli.workserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author windSnowLi
 */
@Getter
@Setter
@ToString
public class TaskRecord implements Serializable {
    private int recordId;
    private int taskId;
    private int userId;
    private Date submissionTime;
    private Student student;
}
