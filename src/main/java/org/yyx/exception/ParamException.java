package org.yyx.exception;

/**
 * 参数类型的异常
 * Create by 叶云轩 at 2018/1/24 17:53
 * Concat at tdg_yyx@foxmail.com
 */
public class ParamException extends RuntimeException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 779357910085085955L;

    public ParamException(String message) {
        super(message);
    }
}
