package com.bs.metal.common.vo;

public enum ResultCodeEnum {
    SUCCESS("0","成功"),
    ERROR("-1","系统异常"),
    PARAM_ERROR("1001","参数异常"),
    USER_EXIST_ERROR("2001","用户已存在"),
    USER_NAME_ERROR("2002","用户名错误"),
    USER_PASSWORD_ERROR("2003","密码错误"),
    USER_NO_LOGIN("2004","未登录"),
    USER_NOTIME_LOGIN("2005","登录过期"),
    TOKEN_NO("2006","登录过程中遇到错误  请重新登录"),
    FILE_NULL("2007","文件名为空");

    public String code;// 返回码
    public String msg;//错误消息

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
