package org.yyx.util.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.util.string.UtilString;

public class UtilMD5Test {


    /**
     * UtilMD5Test 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilMD5Test.class);

    @Test
    public void encryptString() {
        String str = "1238920735hdfskljoewiqur21";
        String s = UtilMD5.encryptString(str, UtilString.randomSalt(8));
        LOGGER.info("[密文] {}", s);
    }
}