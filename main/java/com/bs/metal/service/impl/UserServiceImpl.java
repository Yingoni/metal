package com.bs.metal.service.impl;

import com.bs.metal.Dao.UserDAO;
import com.bs.metal.common.util.MD5Util;
import com.bs.metal.common.vo.ResultCodeEnum;
import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.entity.User;
import com.bs.metal.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    /**
     * 用户注册
     * 考虑并发情况下加入 事务 和同步锁
     * 同步锁 ：
     * this 代表当前用户Service
     * spring默认是单例所以可以直接用this
     * synchronization
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    @Transactional //添加事务 mysql默认隔离级别是可重复读
    public ResultVO toRegister(String name, String password) {
        synchronized (this) {
            //1.查看用户名是否被注册
            int count = userDAO.selectNameCount(name);
            if (count > 0) {
                //返回用户已存在
                return ResultVO.resultVO(ResultCodeEnum.USER_EXIST_ERROR);
            } else {
                //2.没被注册，就保存用户
                User user = new User();
                user.setUserName(name);
                user.setPassword(MD5Util.md5(password));
                user.setCreateTime(new Date());
                int i = userDAO.saveUser(user);
                if (i > 0) {
                    return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
                } else {
                    log.error("用户保存失败");
                    return ResultVO.resultVO(ResultCodeEnum.ERROR);
                }
            }
        }
    }

    /**
     * 用户登录
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public ResultVO checkLogin(String name, String password) {
        User user = userDAO.selectName(name);
        if (user == null) {
            //用户名不存在
            return ResultVO.resultVO(ResultCodeEnum.USER_NAME_ERROR);
        } else {
            //用户名存在 验证密码是否正确(把传参的password进行MD5加密与数据库查出的密码进行比较)
            String md5Pw = MD5Util.md5(password);

            if (md5Pw.equals(user.getPassword())) {
                //使用jwt规则生成token
                //生成jwt容器
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(user.getUserName())//主题 token中 携带数据 用用户名作为token名
                        .setIssuedAt(new Date())//token生成时间
                        .setId(user.getId() + "")//设置token id为用户id + “”
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))//设置token过期时间 24小时
                        .signWith(SignatureAlgorithm.HS256, "Xyy123456") //设置加密方式和密码
                        .compact();

                return ResultVO.resultVO(ResultCodeEnum.SUCCESS, token);
            } else {
                return ResultVO.resultVO(ResultCodeEnum.USER_PASSWORD_ERROR);
            }
        }
    }

}
