package org.yyx.util.string;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilStringTest {

    /**
     * UtilStringTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 17:45
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilStringTest.class);

    @Test
    public void isBlank() {
        boolean blank = UtilString.isBlank("");
        LOGGER.info("[blank] {}", this.getClass().getName(), blank);
    }

    @Test
    public void randomSalt() {
        String s = UtilString.randomSalt();
        LOGGER.info("[salt] {}", s);
    }

    @Test
    public void randomSaltWithNum() {
        String s = UtilString.randomSalt(6);
        LOGGER.info("[salt] {}", s);
    }

    @Test
    public void randomUUID() {
        String s = UtilString.randomUUID();
        LOGGER.info("[UUID] {}", s);

    }
}
