package org.yyx.util.maven;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.util.file.io.UtilFile;
import org.yyx.util.string.UtilString;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static ch.qos.logback.core.rolling.helper.FileFilterUtil.isEmptyDirectory;
import static org.yyx.constant.maven.MavenFileConstant.$;
import static org.yyx.constant.maven.MavenFileConstant.LAST_UPDATED;
import static org.yyx.constant.maven.MavenFileConstant.M2_HOME;
import static org.yyx.constant.maven.MavenFileConstant.MAVEN_HOME;
import static org.yyx.constant.maven.MavenFileConstant.UN_KNOWN;

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
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private UtilMavenClean() {
    }


    /**
     * 检查环境
     *
     * @param fileName 输入的目录名
     */
    private static void checkEnv(final String fileName) {
        Map<String, String> environmentMap = System.getenv();
        boolean containsM2Home = environmentMap.containsKey(M2_HOME);
        boolean containsMavenHome = environmentMap.containsKey(MAVEN_HOME);
        if (!containsM2Home && !containsMavenHome) {
            // 两个都不包含  说明 没有配置 MAVEN_HOME 或 M2_HOME 中的任意一项
            return;
        }
        String m2Home = null;
        if (containsM2Home) {
            // 配置中包含 M2_HOME
            m2Home = environmentMap.get(M2_HOME);
        }
        String mavenHome = null;
        if (containsMavenHome) {
            // 配置中包含 MAVEN_HOME
            mavenHome = environmentMap.get(MAVEN_HOME);
        }
        boolean m2HomeCheckBlank = UtilString.isBlank(m2Home);
        boolean mavenHomeCheckBlank = UtilString.isBlank(mavenHome);
        // 默认认为，环境变量中配置了，所以两个字段都不为空
        if (!m2HomeCheckBlank && !mavenHomeCheckBlank) {
            if (m2Home.equals(mavenHome)) {
                // 说明 m2Home 和 mavenHome配置的是同一个路径
                if (m2Home.equals(fileName)) {
                    // 三个路径是同一个路径
                    return;
                } else {
                    // cd M2_HOME
                    String localRepository = getLocalRepository(m2Home);
                    doClean(localRepository);
                }
            } else {
                // 配置的内容不同
                if (UtilString.startWith(m2Home, $ + mavenHome, "%" + mavenHome)) {
                    // 说明 M2_HOME 配置的是 %MAVEN_HOME 或者是 $MAVEN_HOME windows 和 linux的区别
                    m2Home = mavenHome;
                    if (m2Home.equals(fileName)) {
                        // 三个路径是同一个路径
                        return;
                    } else {
                        // cd M2_HOME
                        LOGGER.info("[checkEnv] -> [m2Home] {}", m2Home);
                        String localRepository = getLocalRepository(m2Home);
                        doClean(localRepository);
                    }
                } else if (UtilString.startWith(mavenHome, $ + m2Home, "%" + m2Home)) {
                    // 说明 MAVEN_HOME 配置的是 %M2_HOME 或者是 M2_HOME windows 和 linux的区别
                    mavenHome = m2Home;
                    if (mavenHome.equals(fileName)) {
                        // 三个路径是同一个路径
                        return;
                    } else {
                        // cd MAVEN_HOME
                        LOGGER.info("[checkEnv] -> [mavenHome] {}", mavenHome);
                        String localRepository = getLocalRepository(mavenHome);
                        doClean(new File(localRepository));
                    }
                } else {
                    // m2Home 和 mavenHome是独立的 且不是同一个路径  cd M2_HOME cd MAVEN_HOME
                    String localRepository = getLocalRepository(m2Home);
                    doClean(localRepository);
                    localRepository = getLocalRepository(mavenHome);
                    doClean(localRepository);
                }
            }
        }
    }

    /**
     * 清理Maven家目录下无用目录与文件
     *
     * @param mavenRepositoryPath maven资源库路径
     */
    public static void clean(final String mavenRepositoryPath) {
        // 将路径以文件的形式表示
        File mavenRepositoryFile = new File(mavenRepositoryPath);
        // 一个线程去找输入文件下目录路径
        singleThreadPool.execute(() -> {
            if (mavenRepositoryFile.exists()) {
                // 说明文件存在
                doClean(mavenRepositoryFile);
            }
        });
        // 另一个线程去自动查找环境变量
        singleThreadPool.execute(() -> {
            checkEnv(mavenRepositoryPath);
        });
    }

    /**
     * 执行清理工作的方法
     *
     * @param mavenRepositoryFile Maven的资源库文件
     */
    private static void doClean(final File mavenRepositoryFile) {
        mavenRepositoryFile.listFiles(pathname -> {
            // 判断文件是否是目录
            boolean directory = pathname.isDirectory();
            // 文件名转换为小写
            String name = pathname.getName().toLowerCase();
            if (directory) {
                // 是目录，判断目录是否以 $,unknow开头,如果以这些开头,说明是无用目录
                if (UtilString.startWith(name, $, UN_KNOWN)) {
                    boolean deleteFile = UtilFile.deleteFile(pathname);
                    LOGGER.info("[doClean] -> [当前目录是无用目录 删除目录] {} -> {}", pathname.getPath(), deleteFile ? "成功" : "失败");
                    return false;
                }
                boolean emptyDirectory = isEmptyDirectory(pathname);
                if (!emptyDirectory) {
                    clean(pathname.getPath());
                } else {
                    boolean deleteFile = UtilFile.deleteFile(pathname);
                    LOGGER.info("[doClean] -> [当前目录是空目录 删除目录] {} -> {}", pathname.getPath(), deleteFile ? "成功" : "失败");
                }
            } else {
                if (UtilString.endWidth(name, LAST_UPDATED)) {
                    boolean deleteFile = UtilFile.deleteFile(pathname);
                    LOGGER.info("[doClean] -> [删除文件] {} -> {}", pathname.getPath(), deleteFile ? "成功" : "失败");
                }
            }
            return false;
        });
    }

    /**
     * 封装一个clean方法，用于处理资源库文件地址为空的逻辑
     *
     * @param repositoryPath
     */
    private static void doClean(String repositoryPath) {
        if (!UtilString.isBlank(repositoryPath)) {
            doClean(new File(repositoryPath));
        }
    }

    /**
     * 通过 mavenHome 查询 settings.xml 中配置的 localRepositoryPath
     *
     * @param mavenHome mavenHome
     * @return localRepositoryPath
     */
    protected static String getLocalRepository(String mavenHome) {
        String settingsFile = mavenHome + "/settings.xml";
        File file = new File(settingsFile);
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            LOGGER.error("[getLocalRepository] -> [读取文件失败] {}", settingsFile);
            return null;
        }
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator("localRepository");
        if (iterator.hasNext()) {
            Element next = (Element) iterator.next();
            return next.getStringValue();
        }
        return null;
    }
}
