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
     *
     * @return
     */
    @Override
    @Transactional //添加事务 mysql默认隔离级别是可重复读
    public ResultVO toRegister(User user) {
        synchronized (this) {
            //1.查看用户名是否被注册
            int count = userDAO.selectNameCount(user.getUserName());
            if (count > 0) {
                //返回用户已存在
                return ResultVO.resultVO(ResultCodeEnum.USER_EXIST_ERROR);
            } else {
                //2.没被注册，就保存用户
                User user1 = new User();
                user1.setUserName(user.getUserName());
                user1.setLikeName(user.getUserName());
                user1.setPassword(MD5Util.md5(user.getPassword()));
                user1.setCreateTime(new Date());
                user1.setUpdateTime(new Date());
                int i = userDAO.saveUser(user1);
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
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultVO checkLogin(String username, String password) {
        User user = userDAO.selectName(username);
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

                return new ResultVO("0",token,user);
            } else {
                return ResultVO.resultVO(ResultCodeEnum.USER_PASSWORD_ERROR);
            }
        }
    }

    /**
     * 修改密码
     * @return
     */
    @Override
    public ResultVO updatePass(User.UpdatePassBean upb) {
        Integer userId = upb.getId();
        String passnum = upb.getPassnum();
        String password = upb.getPassword();
        User user =  userDAO.selectUserById( userId);
       if (user == null ){
           return ResultVO.resultVO(ResultCodeEnum.USER_NO_LOGIN);
       }else{
           String pass = MD5Util.md5(passnum);
           if (pass.equals(user.getPassword())){
               String passMD5 = MD5Util.md5(password);
               user.setPassword(passMD5);
                user.setUpdateTime(new Date());
               int row = userDAO.updatePassWord(user);
               if (row>0){
                   return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
               }else{
                   log.error("新密码入库失败");
                   return ResultVO.resultVO(ResultCodeEnum.ERROR);
               }

           }else {
               return ResultVO.resultVO(ResultCodeEnum.USER_PASSWORD_ERROR);
           }
       }

    }

    /**
     * 修改用户个人信息
     * @param user
     * @return
     */
    @Override
    public ResultVO updatePersona(User user) {
        user.setUpdateTime(new Date());
       int  row = userDAO.updatePersona(user);
       if (row>0){
           return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
       }
        return ResultVO.resultVO(ResultCodeEnum.PARAM_ERROR);
    }

    /**
     * 保存用户imgId
     * @param user
     * @return
     */
    @Override
    public ResultVO updateImg(User user) {
        user.setUpdateTime(new Date());
        int i = userDAO.updatePicture(user);
        if (i>0){
            return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
        }else {
            return new ResultVO("40001","保存失败",null);
        }
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @Override
    public ResultVO selectUser(Integer id) {
        User user = userDAO.selectUserById(id);
        if (user !=null){
            return ResultVO.resultVO(ResultCodeEnum.SUCCESS,user);
        }else {
            return ResultVO.resultVO(ResultCodeEnum.ERROR);
        }

    }

}
