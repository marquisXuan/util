package org.yyx.exception.file;

/**
 * 文件异常
 * Create by 叶云轩 at 2018/1/24 19:29
 * Concat at tdg_yyx@foxmail.com
 */
public class FileException extends RuntimeException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -56113286449518127L;

    public FileException(String message) {
        super(message);
    }
}