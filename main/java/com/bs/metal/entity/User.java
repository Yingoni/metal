package com.bs.metal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 用户类
 */

@Data //自动生成成员变量的set和get方法
@NoArgsConstructor//无参构造函数
@AllArgsConstructor//所有有参构造函数
public class User {
    private Integer id; //主键  id
    private String userName;  //用户名
    private String password; // 密码
    private String sex;//性别
    private String likeName; //昵称
    private Long imgId; // 头像 id
    private String email; // 邮箱地址
    private Date createTime; // 创建时间
    private Date updateTime; //修改时间

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", likeName='" + likeName + '\'' +
                ", imgId=" + imgId +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
