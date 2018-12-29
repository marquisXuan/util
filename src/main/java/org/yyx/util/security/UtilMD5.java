package org.yyx.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.exception.ParamException;
import org.yyx.util.string.UtilString;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018-12-29-17:32
 */
public class UtilMD5 {
    /**
     * UtilMD5 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilMD5.class);

    /**
     * 私有构造
     */
    private UtilMD5() {
    }

    /**
     * 加密字符串的方法
     *
     * @param proclaimed 明文
     * @param randomStr  随机字符串
     * @return 密文
     * @throws ParamException 传入参数异常
     */
    public static String encryptString(String proclaimed, String randomStr) throws ParamException {
        if (UtilString.isBlank(proclaimed)) {
            throw new ParamException("加密内容不能为空");
        }
        // 获取待加密字符串长度
        int length = proclaimed.length();
        // 截取明文0 - l/2 区间
        String prefix = proclaimed.substring(0, length / 2);
        String tempProclaimed = prefix + (randomStr == null ? "" : randomStr) + proclaimed;
        return encryptString(tempProclaimed);
    }

    /**
     * 加密字符串的方法
     *
     * @return 密文
     * @throws ParamException 传入参数异常
     */
    private static String encryptString(String proclaimed) throws ParamException {
        if (UtilString.isBlank(proclaimed)) {
            throw new ParamException("加密内容不能为空");
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("[未找到摘要实例] {}", e.getMessage());
            return null;
        }
        byte[] digest = md5.digest(proclaimed.getBytes());
        return UtilString.byteToHex(digest);
    }
}
