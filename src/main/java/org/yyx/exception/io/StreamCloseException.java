package org.yyx.exception.io;

/**
 * 流关闭异常
 * <p>
 * Create by 叶云轩 at 2018/3/23 23:10
 * Concat at tdg_yyx@foxmail.com
 */
public class StreamCloseException extends StreamException {

    private static final long serialVersionUID = -8444882662215239487L;

    public StreamCloseException(String message) {
        super(message);
    }
}
