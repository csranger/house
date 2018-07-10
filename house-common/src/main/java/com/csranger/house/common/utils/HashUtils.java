package com.csranger.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * 写一个工具类，用于加密
 */
public class HashUtils {

    // HashFunction 接口：接口方法用于将各种类型数据转换成 HashCode，理解成加密；HashCode代表不可变的 hash 值
    private static final HashFunction FUNCTION = Hashing.md5();

    private static final String salt = "csranger.com";

    public static String encryPassword(String password) {
        HashCode hashCode = FUNCTION.hashString(password + salt, Charset.forName("UTF-8"));
        return hashCode.toString();
    }

}
