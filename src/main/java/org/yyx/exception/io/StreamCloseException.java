package org.yyx.exception.io;

/**
 * 流关闭异常
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/3/23 23:10
 */
public class StreamCloseException extends StreamException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -8444882662215239487L;

    public StreamCloseException(String message) {
        super(message);
    }
}
