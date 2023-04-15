package com.bs.metal.common.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * MD5加密工具类
 *
 * @auther Bongo
 * @create 2023/4/12 21:42
 */
@Slf4j
public class MD5Util {
    public static String md5(String password) {
        //生成MD5容器
        try {
            //选择加密方式
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            return new BigInteger(1, md5.digest()).toString();

        } catch (NoSuchAlgorithmException e) {
            log.error("md5加密异常", e);
        }
        return null;
    }
}
