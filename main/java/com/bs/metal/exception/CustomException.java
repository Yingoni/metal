package com.bs.metal.exception;

import com.bs.metal.common.ResultCode;
import lombok.Data;


@Data
public class CustomException extends RuntimeException{
    private String code;
    private String msg;

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
    }

}
