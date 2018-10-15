package org.yyx.util.http;

/**
 * 基础响应数据结构封装
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/15-21:58
 */
public interface BaseResponse<T> {
    /**
     * 获取响应码
     *
     * @return 响应码
     */
    Long getCode();

    /**
     * 获取返回的数据
     *
     * @return 待返回的数据
     */
    T getData();

    /**
     * 获取响应码的英文说明
     *
     * @return 响应码的英文说明
     */
    String getDescription();

    /**
     * 获取响应码的中文说明
     *
     * @return 响应码的中文说明
     */
    String getMsg();

    /**
     * 分页时获取分页数据方法
     *
     * @return 获取分页数据
     */
    T getRows();

    /**
     * 分页时获取数据总量方法
     *
     * @return 获取数据总量
     */
    Long getTotal();
}
