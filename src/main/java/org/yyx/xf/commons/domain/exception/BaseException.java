package org.yyx.xf.commons.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常基础类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2019-02-16-00:14
 */
public class BaseException extends RuntimeException {

    /**
     * BaseException 日志输出
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseException.class);
    private static final long serialVersionUID = -7034897190745767639L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        LOGGER.info("[BaseException] -> [message] {} ", message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.info("[BaseException] -> [message] {} ", message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
