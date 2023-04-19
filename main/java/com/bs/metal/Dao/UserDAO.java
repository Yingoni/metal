package com.bs.metal.Dao;

import com.bs.metal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDAO {
    //根据用户名统计条数
    int selectNameCount(String name);
    //保存新用户
    int saveUser(User user);
    //根据用户名查询用户
    User selectName(String name);
    //根据id查询用户
    User selectUserById(@Param("userId") Integer userId);
    //修改密码
    int updatePassWord(User user);

    //修改头像
    int updatePicture(User user);

    int updatePersona(User user);
}
