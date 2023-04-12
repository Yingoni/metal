package com.bs.metal.common;

public enum ResultCode {
    SUCCESS("0","成功"),
    ERROR("-1","系统异常"),
    PARAM_ERROR("1001","参数异常"),
    USER_EXIST_ERROR("2001","用户已存在"),
    USER_NAME_ERROR("2002","用户名错误"),
    USER_PASSWORD_ERROR("2003","密码错误");

    public String code;// 返回码
    public String msg;//错误消息

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
