package org.yyx.util.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.util.file.io.UtilFile;

import java.io.File;

import static ch.qos.logback.core.rolling.helper.FileFilterUtil.isEmptyDirectory;
import static org.yyx.constant.maven.MavenFileConstant.$;
import static org.yyx.constant.maven.MavenFileConstant.LAST_UPDATED;

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
     * @param mavenRepositoryPath maven资源库路径
     * @return 返回清理成功与否 true:成功 false:失败
     */
    public static boolean clean(String mavenRepositoryPath) {
        File mavenRepositoryFile = new File(mavenRepositoryPath);
        File[] files = mavenRepositoryFile.listFiles(pathname -> {
            boolean directory = pathname.isDirectory();
            // 文件名小写
            String name = pathname.getName().toLowerCase();
            if (directory) {
                if (name.startsWith($)) {
                    LOGGER.info("[当前目录是无用目录] {}", name);
                    UtilFile.deleteFile(pathname);
                    return false;
                }
                boolean emptyDirectory = isEmptyDirectory(pathname);
                if (!emptyDirectory) {
                    clean(pathname.getPath());
                } else {
                    LOGGER.info("[目录 {} 是空目录] 删除", pathname);
                    UtilFile.deleteFile(pathname);
                }
            } else {
                if (name.endsWith(LAST_UPDATED)) {
                    LOGGER.info("[删除文件] {}", pathname);
                    UtilFile.deleteFile(pathname);
                }
            }
            return false;
        });
        return false;
    }
}
