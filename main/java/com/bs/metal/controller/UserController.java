package com.bs.metal.controller;

import com.bs.metal.common.vo.ResultVO;
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
     * @return
     */
    @PostMapping("/metal/register")
    public ResultVO register(@RequestBody User user){
        ResultVO resultVO = userService.toRegister(user);
        return resultVO;
    }

    @GetMapping("/metal/login")
    public ResultVO toLogin(@RequestParam(value = "username",required = true) String username ,
                            @RequestParam(value = "password",required = true) String password){
        ResultVO resultVO = userService.checkLogin(username, password);
        return resultVO;
    }


}
