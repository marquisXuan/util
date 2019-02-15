package org.yyx.xf.tool.string.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

import static org.yyx.xf.tool.string.domain.constant.StringConstant.ALL_STRING;
import static org.yyx.xf.tool.string.domain.constant.StringConstant.DEFAULT_SALT_COUNT;

/**
 * 与字符串相关的工具类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/3 - 下午5:54
 */
public class UtilString {

    /**
     * UtilString 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilString.class);

    private UtilString() {
    }

    /**
     * 将Byte数组转成字符串
     *
     * @param digest Byte数组
     * @return 字符串
     */
    public static String byteToHex(byte[] digest) {
        StringBuffer hexStringBuffer = new StringBuffer();
        String shaHex;
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexStringBuffer.append(0);
            }
            hexStringBuffer.append(shaHex);
        }
        String hexString = hexStringBuffer.toString();
        LOGGER.info("[ByteToHex] Success：{}", hexString);
        return hexString;
    }

    /**
     * 判断一个字符串是否包含任一列举的后缀
     *
     * @param checkStr 待检查字符串
     * @param params   后缀
     * @return true: 包含任一列举的后缀值 false:不包含其中的任意一个后缀
     */
    public static boolean endWidth(String checkStr, String... params) {
        for (int i = 0; i < params.length; i++) {
            if (checkStr.endsWith(params[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 待判断字符串
     * @return true：为空 false：不为空
     */
    public static boolean isBlank(String str) {
        if (str == null || "" .equals(str.trim())) {
            return true;
        }
        return str.length() == 0;
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
     * @return 盐
     */
    public static String randomSalt(int count) {
        char[] saltArray = ALL_STRING.toCharArray();
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < count; i++) {
            salt.append(saltArray[random.nextInt(saltArray.length)]);
        }
        String arg = salt.toString();
        LOGGER.info("[随机字符串] {}", arg);
        return arg;
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
     * 判断一个字符串是否包含任一列举的前缀
     *
     * @param checkStr 待检查字符串
     * @param params   前缀
     * @return true: 包含任一列举的前缀值 false:不包含其中的任意一个前缀
     */
    public static boolean startWith(String checkStr, String... params) {
        for (int i = 0; i < params.length; i++) {
            if (checkStr.startsWith(params[i])) {
                return true;
            }
        }
        return false;
    }

}
