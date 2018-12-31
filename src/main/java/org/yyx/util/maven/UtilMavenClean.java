package org.yyx.util.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 清理Maven目录下.lastUpdated Unkonw 等无用文件或目录
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018-12-31-22:26
 */
public class UtilMavenClean {
    /**
     * UtilMavenClean 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilMavenClean.class);

    private UtilMavenClean() {
    }

    /**
     * 清理Maven家目录下无用目录与文件
     *
     * @param m2Home m2Home路径
     * @return 返回清理成功与否
     */
    public static boolean clean(String m2Home) {
        Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            Object o = enumeration.nextElement();
            LOGGER.info("[o] {}", o);
        }
        Map<String, String> getenv = System.getenv();
        Set<Map.Entry<String, String>> entries = getenv.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            LOGGER.info("[key - value] {} - {}", entry.getKey(), entry.getValue());
        }
        return false;
    }
}
