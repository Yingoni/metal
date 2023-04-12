package com.bs.metal.service.impl;

import com.bs.metal.Dao.UserDAO;
import com.bs.metal.common.ResultCode;
import com.bs.metal.entity.User;
import com.bs.metal.exception.CustomException;
import com.bs.metal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    /**
     * 用户注册
     * @param user 返回user
     * @return
     */
    @Override
    public User add(User user) {
        //1.判断数据库中用户名是否唯一
        int row = userDAO.selectNameCount(user.getUserName());
        if (row > 0){
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        int i = userDAO.saveUser(user);
        if (i<1 ){
            log.info("用户保存失败");
            throw new CustomException(ResultCode.ERROR);
        }

        return userDAO.selectName(user.getUserName());
    }
}
