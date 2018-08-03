package org.yyx.util.string;

import java.util.Random;
import java.util.UUID;

import static org.yyx.constant.StringConstant.ALL_STRING;
import static org.yyx.constant.StringConstant.DEFAULT_SALT_COUNT;

/**
 * 与字符串相关的工具类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/3 - 下午5:54
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
        return randomSalt(DEFAULT_SALT_COUNT);
    }

    /**
     * 生成随机盐工具类
     *
     * @param count 盐的位数
     *
     * @return 盐
     */
    public static String randomSalt(int count) {
        char[] saltArray = ALL_STRING.toCharArray();
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < count; i++) {
            salt.append(saltArray[random.nextInt(saltArray.length)]);
        }
        return salt.toString();
    }
}
