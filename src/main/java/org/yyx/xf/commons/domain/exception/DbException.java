package org.yyx.xf.commons.domain.exception;

/**
 * 数据库异常
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2020/3/18 9:58 下午
 */
public class DbException extends RuntimeException {

    public DbException() {
        super();
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    protected DbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
