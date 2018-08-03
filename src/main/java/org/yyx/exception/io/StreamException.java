package org.yyx.exception.io;

/**
 * 流异常
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/3/23 23:00
 */
public class StreamException extends RuntimeException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 7832328891924088567L;

    public StreamException(String message) {
        super(message);
    }
}
