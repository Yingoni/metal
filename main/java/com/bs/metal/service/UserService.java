package com.bs.metal.service;

import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.entity.User;

public interface UserService {

    //用户注册
    ResultVO toRegister(User user);
    //用户登录
    ResultVO checkLogin(String username,String password);
    //修改密码
    ResultVO updatePass(User.UpdatePassBean upb);
//修改用户信息
    ResultVO updatePersona(User user);
//保存用户imgid
    ResultVO updateImg(User user);
//查询用户信息
    ResultVO selectUser(Integer id);
}
