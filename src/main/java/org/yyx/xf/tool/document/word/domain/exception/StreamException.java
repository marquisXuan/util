package org.yyx.xf.tool.document.word.domain.exception;

import org.yyx.xf.commons.domain.exception.BaseException;

/**
 * 流异常
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/3/23 23:00
 */
public class StreamException extends BaseException {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 7832328891924088567L;

    public StreamException(String message) {
        super(message);
    }
}
