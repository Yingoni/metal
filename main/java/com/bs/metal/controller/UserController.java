package com.bs.metal.controller;

import com.bs.metal.common.Result;
import com.bs.metal.entity.User;
import com.bs.metal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;


    /**
     *注册
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/metal/register")
    public Result<User> register(@RequestBody User user, HttpServletRequest request){
        if (StringUtils.isEmpty(user.getUserName()) ||StringUtils.isEmpty(user.getUserPw())){
            return Result.error("2003", "账号或密码为空");
        }
        User register = userService.add(user);
        HttpSession session = request.getSession();
        session.setAttribute("USER",register);
        session.setMaxInactiveInterval(60*60*24);
        return Result.success(register);
    }
}
