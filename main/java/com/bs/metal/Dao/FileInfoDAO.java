package com.bs.metal.Dao;

import com.bs.metal.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Bongo
 * @create 2023/4/15 18:59
 */
@Mapper
public interface FileInfoDAO {
    /**
     * 保存上传图片
     */
    int save(FileInfo fileInfo);

    /**
     * 根据id查询对象
     */
    FileInfo selectById(String id);
}
