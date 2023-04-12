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
    private Integer id;
    private String userName;
    private String password;
    private String sex;
    private String userImg;
    private String email;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", userImg='" + userImg + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
