package org.yyx.util.file.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilFileTest {

    /**
     * UtilFileTest 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:24
     * Concat at yCountJavaXuan@outlook.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilFileTest.class);

    @Test
    public void deleteFile() {
        boolean b = UtilFile.deleteFile("/Users/xuan/Desktop/a.txt");
        LOGGER.info("[b] {}", b);
    }
}