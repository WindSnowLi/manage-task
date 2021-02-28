package com.windsnowli.workserver.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author windSnowLi
 */
public class Msg implements Serializable {
    public final static int CODE_SUCCESS = 1;
    public final static int CODE_FAIL = -1;
    public final static String MSG_SUCCESS = "请求成功";
    public final static String MSG_FAIL = "请求失败";
    public final static String LOGIN_PASSWORD_FAIL = "密码错误";
    public final static String LOGIN_NUMBER_FAIL = "账户不存在";
    /**
     * 返回状态码
     */
    @Getter
    private int code;
    /**
     * 返回信息
     */
    @Getter
    private String msg;
    /**
     * 返回信息内容
     */
    @Getter
    private String content;

    public Msg setCode(int code) {
        this.code = code;
        return this;
    }

    public Msg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Msg setContent(String content) {
        this.content = content;
        return this;
    }

    private Msg(int code, String msg, String content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    private Msg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Msg() {
    }

    public static Msg makeMsg(int code, String msg, String content) {
        return new Msg(code, msg, content);
    }

    public static String makeJsonMsg(int code, String msg, Object content) {
        return JSONObject.toJSONString(new Msg(code, msg, JSONObject.toJSONString(content)));
    }

    public static String getFailMsg() {
        return Msg.makeJsonMsg(Msg.CODE_FAIL, Msg.MSG_FAIL, null);
    }

    public static String getSuccessMsg() {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, Msg.MSG_SUCCESS, null);
    }

    public static Msg parseMsg(String msg) {
        return JSONObject.parseObject(msg, Msg.class);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


    public static String getSuccessMsg(Object object) {
        return Msg.makeJsonMsg(Msg.CODE_SUCCESS, MSG_SUCCESS, object);
    }
}
