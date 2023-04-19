package com.bs.metal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户和识别图片的中间类
 *鉴别前和鉴别后的文件名一致，
 *
 * @auther Bongo
 * @create 2023/4/15 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFileInfo {
    //   id
    private Integer id;
    //    用户id
    private Integer userId;
    //    上传图片的id 用  “,” 隔开
    private String files;

    private List<Long> fileList;
}
