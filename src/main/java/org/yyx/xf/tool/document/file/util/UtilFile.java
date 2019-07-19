package org.yyx.xf.tool.document.file.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.yyx.xf.tool.date.util.UtilDate;
import org.yyx.xf.tool.document.file.domain.exception.FileException;
import org.yyx.xf.tool.string.util.UtilString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import static ch.qos.logback.core.rolling.helper.FileFilterUtil.isEmptyDirectory;

/**
 * 与文件操作相关的工具类
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/1/24 17:19
 */
public class UtilFile {

    /**
     * UtilFile 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:26
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilFile.class);


    private UtilFile() {
    }

    /**
     * 复制文件
     *
     * @param file     源文件
     * @param filePath 目标路径 + 文件名
     * @return 复制状态
     */
    public static boolean copyFileToDirectory(File file, String filePath) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 输入流
            fileInputStream = new FileInputStream(file);
            // 输出流
            fileOutputStream = new FileOutputStream(filePath);
            // 从FileInputStream中获取Channel
            FileChannel fileInputStreamChannel = fileInputStream.getChannel();
            // 从FileOutputStream中获取Channel
            FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
            // 定义一个偏移量
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (true) {
                allocate.clear();
                int read = fileInputStreamChannel.read(allocate);
                if (read == -1) {
                    break;
                }
                allocate.flip();
                fileOutputStreamChannel.write(allocate);
            }
            return true;
        } catch (FileNotFoundException e) {
            LOGGER.error("[文件不存在] {}", file.getName());
            throw new FileException(file.getName() + "不存在");
        } catch (IOException e) {
            LOGGER.error("[IO操作异常] {}", e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    LOGGER.error("[关闭资源异常] {}", e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("[关闭资源异常] {}", e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 删除状态
     */
    public static boolean deleteFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            return deleteFile(file);
        } catch (Exception e) {
            LOGGER.error("[没有找到对应文件] {}", filePath);
            throw new FileException("没有找到对应文件");
        }
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return 删除状态
     */
    public static boolean deleteFile(File file) {
        String filePath = file.getPath();
        try {
            if (file.exists()) {
                // 文件是目录么？
                boolean directory = file.isDirectory();
                if (directory) {
                    // 是目录
                    boolean emptyDirectory = isEmptyDirectory(file);
                    if (emptyDirectory) {
                        // 是空目录
                        return file.delete();
                    } else {
                        LOGGER.warn("[deleteFile] -> [文件不为空] {}", filePath);
                        file.listFiles(pathname -> {
                            if (pathname.isDirectory()) {
                                // 子文件是目录
                                deleteFile(pathname);
                                return false;
                            } else {
                                boolean delete = pathname.delete();
                                LOGGER.info("[accept] -> [删除目录下文件] {} -> {}", pathname.getPath(), delete ? "成功" : "失败");
                                return delete;
                            }
                        });
                    }
                }
                return file.delete();
            } else {
                LOGGER.warn("[deleteFile] -> [文件不存在] {}", filePath);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("[没有找到对应文件] {}", filePath);
            throw new FileException("没有找到对应文件");
        }
    }

    /**
     * 文件上传至本地的方法
     *
     * @param multipartFile 待上传文件
     * @param filePath      文件保存目录 全路径
     * @return 将要保存的服务器文件名, 如果返回NULL, 说明文件上传失败
     */
    public static String uploadFile(MultipartFile multipartFile, String filePath) {
        if (multipartFile == null || multipartFile.isEmpty() || multipartFile.getSize() == 0) {
            throw new FileException("不能上传空文件");
        }
        LOGGER.info("[文件上传] 上传目录：{},上传文件名：{}", filePath, multipartFile.getOriginalFilename());
        String fileName = getUniqueFileName(multipartFile.getOriginalFilename());
        LOGGER.info("[文件上传] 生成服务器保存文件名：{}", fileName);
        File directory = new File(filePath);
        while (true) {
            if (!directory.exists()) {
                LOGGER.info("[文件上传] 上传目录不存在，开始创建目录");
                boolean mkdirs = directory.mkdirs();
                LOGGER.info("[目录创建] {} ： {}", directory, (mkdirs ? "成功" : "失败"));
            } else {
                break;
            }
        }
        try {
            File file = new File(directory.getPath(), fileName);
            multipartFile.transferTo(file);
            if (file.exists()) {
                LOGGER.info("[文件上传] 成功");
                return fileName;
            }
        } catch (IOException e) {
            LOGGER.error("[文件上传异常] {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取唯一文件名
     * 默认不保留原文件名
     *
     * @param originalFilename 原文件名
     * @return 服务器文件名
     */
    public static String getUniqueFileName(String originalFilename) {
        return getUniqueFileName(originalFilename, false);
    }

    /**
     * 获取唯一文件名
     *
     * @param originalFilename 原文件名
     * @param keepFileName     是否保留原文件名 true:保留 false:不保留
     * @return 服务器文件名
     */
    public static String getUniqueFileName(String originalFilename, boolean keepFileName) {
        String fileNamePrefix = "";
        String fileNameSuffix = "";
        int lastIndexOf = originalFilename.lastIndexOf(".");
        if (lastIndexOf != -1) {
            // 截取文件后缀
            fileNameSuffix = originalFilename.substring(lastIndexOf);
        }
        if (keepFileName) {
            if (lastIndexOf != -1) {
                // 文件名（不包含后缀）
                fileNamePrefix = originalFilename.substring(0, lastIndexOf) + "_";
            } else {
                fileNamePrefix = originalFilename + "_";
            }
        }
        fileNamePrefix += UtilString.randomUUID();
        StringBuilder sb = new StringBuilder(fileNamePrefix)
                .append(UtilDate.javaUtilDateToString(new Date(), "yyyyMMddHHmmSSS"));
        return sb.append(fileNameSuffix).toString();
    }

    /**
     * 向文件中写内容
     *
     * @param content  内容
     * @param filePath 文件路径
     * @return 文件对象
     */
    public static File writeInFile(String content, String filePath) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fwriter != null) {
                    fwriter.flush();
                    fwriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new File(filePath);
    }

    public static File bytesConvertToFile(byte[] bytes, String filePath) throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.wrap(bytes);
        try {
            channel.write(allocate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(filePath);
    }
}
