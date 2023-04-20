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
    @PostMapping("/metal/user/register")
    public ResultVO register(@RequestBody User user){
        ResultVO resultVO = userService.toRegister(user);
        return resultVO;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/metal/user/login")
    public ResultVO toLogin( @RequestParam String username ,
                             @RequestParam String password){
        ResultVO resultVO = userService.checkLogin(username, password);
        return resultVO;
    }
    /**
     * 微信登录
     */
    @PostMapping("/metal/user/wxreg")
    public ResultVO wxLogin(@RequestBody User user){
        ResultVO resultVO = userService.wxLogin(user);
        return resultVO;
    }

    /**
     * 修改密码
     */
    @PostMapping("/metal/user/updatePassword")
    public ResultVO updatePassWord(@RequestBody User.UpdatePassBean  upb){
      ResultVO resultVO =  userService.updatePass(upb);
        return  resultVO;
    }
    /**
     * 保存头像
     */

    @PostMapping("/metal/user/updateImg")
    public ResultVO updateImg(@RequestBody User user){
        ResultVO resultVO =  userService.updateImg(user);
        return  resultVO;
    }


    /**
     * 修改个人信息
     */

    @PostMapping("/metal/user/updatePersona")
    public ResultVO updatePersona(@RequestBody User user){
        ResultVO resultVO =  userService.updatePersona(user);
        return  resultVO;
    }

    /**
     * 查询用户
     * @param id
     * @return
     */
    @GetMapping("/metal/user/select")
    public ResultVO selectUser(@RequestParam("id") Integer id){
     ResultVO resultVO =   userService.selectUser(id);
        return resultVO;
    }

}
