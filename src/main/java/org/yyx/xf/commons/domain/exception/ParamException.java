package org.yyx.xf.commons.domain.exception;

/**
 * 参数类型的异常
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/1/24 17:53
 */
public class ParamException extends BaseException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 779357910085085955L;

    public ParamException(String message) {
        super(message);
    }
}
