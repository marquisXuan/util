package org.yyx.xf.tool.document.file.domain.exception;

import org.yyx.xf.commons.domain.exception.BaseException;

/**
 * 文件异常
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/1/24 19:29
 */
public class FileException extends BaseException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -56113286449518127L;

    public FileException(String message) {
        super(message);
    }
}