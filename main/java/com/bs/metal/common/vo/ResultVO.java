package com.bs.metal.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回结果工具类
 *
 * @param
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private String code;// 返回代码 "0"成功,"1"失败
    private String msg;//异常信息
    private Object data;//数据


    public static ResultVO resultVO(ResultCodeEnum resultCodeEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.code = resultCodeEnum.code;
        resultVO.msg = resultCodeEnum.msg;
        return resultVO;

    }

    public static ResultVO resultVO(ResultCodeEnum resultCodeEnum ,Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.code = resultCodeEnum.code;
        resultVO.msg = resultCodeEnum.msg;
        resultVO.data = data;
        return resultVO;

    }


}
