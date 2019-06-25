package org.yyx.xf.util.maven;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.xf.tool.build.maven.util.UtilMaven;

public class UtilMavenTest {

    /**
     * UtilMavenTest 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilMavenTest.class);

    @Test
    public void clean() {
        UtilMaven.clean("C:\\Users\\WangYuannian\\.m2\\repository");
    }
}