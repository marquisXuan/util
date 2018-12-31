package org.yyx.util.maven;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilMavenCleanTest {

    /**
     * UtilMavenCleanTest 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilMavenCleanTest.class);

    @Test
    public void clean() {
        UtilMavenClean.clean("/Users/xuan/.m2/repository");
    }
}