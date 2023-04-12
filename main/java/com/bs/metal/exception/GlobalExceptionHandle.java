package com.bs.metal.exception;

import com.bs.metal.common.Result;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@Slf4j
@ControllerAdvice(basePackages = "com.bs.metal.controller")
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request,Exception exception){
        log.info("异常信息",exception);
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(HttpServletRequest request,CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }
}
