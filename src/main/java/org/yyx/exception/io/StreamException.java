package org.yyx.exception.io;

/**
 * 流异常
 * <p>
 * Create by 叶云轩 at 2018/3/23 23:00
 * Concat at tdg_yyx@foxmail.com
 */
public class StreamException extends RuntimeException {

    private static final long serialVersionUID = 7832328891924088567L;

    public StreamException(String message) {
        super(message);
    }
}
