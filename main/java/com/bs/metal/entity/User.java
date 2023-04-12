package com.bs.metal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户类
 */

@Data //自动生成成员变量的set和get方法
@NoArgsConstructor//无参构造函数
@AllArgsConstructor//所有有参构造函数
public class User  {
    private Integer id;
    private String userName;
    private String userPw;
    private String sex;
    private String institude;
    private String email;
    private String researchArea;
    private String type;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPw='" + userPw + '\'' +
                ", sex='" + sex + '\'' +
                ", institude='" + institude + '\'' +
                ", email='" + email + '\'' +
                ", researchArea='" + researchArea + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
