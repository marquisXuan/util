package org.yyx.util.file.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.yyx.exception.FileException;
import org.yyx.util.date.UtilDate;
import org.yyx.util.string.UtilString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * 与文件操作相关的工具类
 * Create by 叶云轩 at 2018/1/24 17:19
 * Concat at tdg_yyx@foxmail.com
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
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            LOGGER.error("[没有找到对应文件] {}", filePath);
            throw new FileException("没有找到对应文件");
        }
        return false;
    }

    /**
     * 文件上传方法
     *
     * @param multipartFile 待上传文件
     * @param filePath      文件保存目录 全路径
     * @return 将要保存的服务器文件名
     */
    public static String uploadFile(MultipartFile multipartFile, String filePath) {
        if (multipartFile == null || multipartFile.isEmpty() || multipartFile.getSize() == 0) {
            throw new FileException("不能上传空文件");
        }
        LOGGER.info("[文件上传] 上传目录：{},上传文件名：{}", filePath, multipartFile.getOriginalFilename());
        String fileName = uniqueFileName(multipartFile.getOriginalFilename());
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
            directory = null;
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
    public static String uniqueFileName(String originalFilename) {
        return uniqueFileName(originalFilename, false);
    }

    /**
     * 获取唯一文件名
     *
     * @param originalFilename 原文件名
     * @param keepFileName     是否保留原文件名 true:保留 false:不保留
     * @return 服务器文件名
     */
    public static String uniqueFileName(String originalFilename, boolean keepFileName) {
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
                .append(UtilDate.javaUtilDateToString(new Date(),
                        "yyyyMMddHHmmSSS"));
        return sb.append(fileNameSuffix).toString();
    }


}
