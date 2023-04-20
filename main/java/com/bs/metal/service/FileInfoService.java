package com.bs.metal.service;

import com.bs.metal.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 图片上传和下载
 * @auther Bongo
 * @create 2023/4/15 18:57
 */
public interface FileInfoService {
    //鉴别图片上传
    ResultVO update(MultipartFile file);


    //文件下载

    ResultVO downloadFile(String id , HttpServletResponse response);
    //头像图片上传
    ResultVO updatePicture( MultipartFile picture);

    //头像下载
    ResultVO downloadPicture(String id, HttpServletResponse response);
}
