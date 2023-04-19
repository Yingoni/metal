package com.bs.metal.Dao;

import com.bs.metal.entity.UserFileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * @auther Bongo
 * @create 2023/4/17 13:04
 */
@Mapper
public interface UserFileInfoDAO {
    //根据用户id 查询 UserFileInfo对象
    UserFileInfo selectByUserId(Integer userId);

    //保存
    int saveUserFileInfo(UserFileInfo userFileInfo );

    //修改
    int updateByUserId(UserFileInfo userFileInfo);

    //更具用户 id 查询全部图片
    String selectFilesByUserId(Integer userId);

    Date selectFilesTime(Integer fileid);
}
