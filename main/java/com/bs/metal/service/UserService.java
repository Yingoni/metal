package com.bs.metal.service;

import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.entity.User;

public interface UserService {

    //用户注册
    ResultVO toRegister(User user);
    //用户登录
    ResultVO checkLogin(String username,String password);
}
