package com.bs.metal.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果工具类
 * @param <T>
 */
@Data
public class Result<T>{
    private String code;// 返回代码 "0"成功,"1"失败
    private String msg;//异常信息
    private T data;// 数据
    private Map map = new HashMap();//动态数据

    public static Result success(){
        Result result = new Result<>();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.msg);
        return result;
    }

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>(data);
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.msg);
        return result;
    }


    public static Result error(){
        Result result = new Result<>();
        result.setCode(ResultCode.ERROR.code);
        result.setMsg(ResultCode.ERROR.msg);
        return result;
    }
    public static Result error(String code,String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public Result(){

    }

    public Result(T data) {
        this.data = data;
    }

    public  Result<T> add(String key, Object value){
        this.map.put(key,value);
        return this;
    }

}
