package org.yyx.util.string;

import org.yyx.constant.StringConstant;

import java.util.Random;
import java.util.UUID;

/**
 * 与字符串相关的工具类
 * Create by 叶云轩 at 2018/1/24 17:20
 * Concat at tdg_yyx@foxmail.com
 */
public class UtilString {

    private UtilString() {
    }

    /**
     * 获取UUID
     *
     * @return 返回不带连字符的UUID
     */
    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 生成4位随机盐工具类
     *
     * @return 盐
     */
    public static String randomSalt() {
        return randomSalt(StringConstant.DEFAULT_SALT_COUNT);
    }

    /**
     * 生成随机盐工具类
     *
     * @param count 盐的位数
     *
     * @return 盐
     */
    public static String randomSalt(int count) {
        char[] saltArray = StringConstant.ALL_STRING.toCharArray();
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < count; i++) {
            salt.append(saltArray[random.nextInt(saltArray.length)]);
        }
        return salt.toString();
    }
}
