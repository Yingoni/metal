package com.bs.metal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/** 文件实体类
 * @auther Bongo
 * @create 2023/4/15 18:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private Long id;
    private String originName;
    private String FileName;
    private Date createTime;
}
