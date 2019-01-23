package org.yyx.util.string;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.yyx.constant.maven.MavenFileConstant.$;
import static org.yyx.constant.maven.MavenFileConstant.UN_KNOWN;

public class UtilStringTest {

    /**
     * UtilStringTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 17:45
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilStringTest.class);

    @Test
    public void endWidth() {

    }

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

    @Test
    public void startWith() {
        String checkStr = "$abc";
        String checkStr2 = "unknowABC";
        String checkStr3 = "sfajljfd";
        boolean b = UtilString.startWith(checkStr, $);
        boolean b1 = UtilString.startWith(checkStr2, UN_KNOWN);
        boolean b2 = UtilString.startWith(checkStr3, $, UN_KNOWN);
        boolean b3 = UtilString.startWith(checkStr, UN_KNOWN, $);
        LOGGER.info("[startWith] -> [$abc startWith $ ] {}", b);
        LOGGER.info("[startWith] -> [unknowABC startWith UN_KNOWN] {}", b1);
        LOGGER.info("[startWith] -> [sfajljfd startWith $ , UN_KNOWN] {}", b2);
        LOGGER.info("[startWith] -> [$abc startWith UN_KNOWN $] {}", b3);
    }
}
