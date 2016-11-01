package com.iware.webshiro.enums;

/**
 * Created by Mahone on 2016/10/31.
 */
public enum EnumCode {

    SYSTEM_SUCCESS_CODE("0","成功"),
    SYSTEM_FAIL_CODE("-1","失败"),
    /** 1000* 系统级别公用的异常错误*/
    SYSTEM_PARAM_ERROR_CODE("10001","参数错误"),

    /** 2000* 系列处理用户异常*/
    USER_LOGIN_FAIL("20001","登陆失败"),
    USER_LOGOUT_FAIL("20002","注销失败");

    private String code;

    private String message;

    private EnumCode(String code ,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return  code;
    }

    public String getMessage(){
        return message;
    }

}
