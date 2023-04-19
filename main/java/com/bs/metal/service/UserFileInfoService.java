package com.bs.metal.service;

import com.bs.metal.common.vo.ResultVO;

/**
 * @auther Bongo
 * @create 2023/4/17 13:13
 */
public interface UserFileInfoService {
//根据用户id 保存或更新 用户和图片的关联
    ResultVO saveUserFileInfo(Integer userId ,Integer fileId);

    ResultVO selectFilesByUserId(Integer userId);

    ResultVO selectFileTimes(Integer fileid);
}
