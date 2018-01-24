package org.yyx.util.file.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.yyx.exception.FileException;
import org.yyx.util.date.UtilDate;
import org.yyx.util.string.UtilString;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 与文件操作相关的工具类
 * Create by 叶云轩 at 2018/1/24 17:19
 * Concat at yCountJavaXuan@outlook.com
 */
public class UtilFile {

    /**
     * UtilFile 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:26
     * Concat at yCountJavaXuan@outlook.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilFile.class);


    private UtilFile() {
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
     * 获取不含连字符的文件名
     *
     * @param originalFilename 原文件名
     * @return 服务器文件名
     */
    public static String uniqueFileName(String originalFilename) {
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String fileNameSuffix = originalFilename.substring(lastIndexOf);
        String fileNamePrefix = UtilString.randomUUID();
        StringBuilder sb = new StringBuilder(UtilDate.javaUtilDateToString(new Date(), "yyyyMMddHHmmSSS"));
        return sb.append(fileNamePrefix).append(fileNameSuffix).toString();
    }

    /**
     * 复制一个文件到指定目录中
     *
     * @param file     源文件
     * @param filePath 目标路径
     * @return 复制状态
     */
    public static boolean copyFileToDirectory(File file, String filePath) {

        return false;
    }
}
