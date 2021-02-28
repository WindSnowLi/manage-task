package com.windsnowli.workserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author windSnowLi
 */
@Getter
@Setter
@ToString
public class Clazz implements Serializable {
    protected int clazzId;
    protected String clazzName;
    protected int clazzYear;
}
