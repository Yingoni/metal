package com.bs.metal.Dao;

import com.bs.metal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDAO {
    int selectNameCount(String name);

    int saveUser(User user);

    User selectName(String name);

}
