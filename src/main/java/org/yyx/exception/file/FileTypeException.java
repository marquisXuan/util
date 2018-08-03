package org.yyx.exception.file;

/**
 * 文件类型不正确异常
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/4/25-上午9:56
 */
public class FileTypeException extends FileException {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -8067248670581813746L;

    public FileTypeException(String message) {
        super(message);
    }
}
