package org.yyx.xf.tool.web.domain.entity;

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
     * 设置响应码
     *
     * @param code 响应码
     */
    void setCode(Long code);

    /**
     * 获取返回的数据
     *
     * @return 待返回的数据
     */
    T getData();

    /**
     * 设置返回的数据
     *
     * @param obj 返回的数据
     */
    void setData(T obj);

    /**
     * 获取响应码的英文说明
     *
     * @return 响应码的英文说明
     */
    String getDescription();

    /**
     * 设置响应码的英文说明
     *
     * @param description 响应码的英文说明
     */
    void setDescription(String description);

    /**
     * 获取响应码的中文说明
     *
     * @return 响应码的中文说明
     */
    String getMsg();

    /**
     * 设置响应码的中文说明
     *
     * @param msg 响应码的中文说明
     */
    void setMsg(String msg);

    /**
     * 分页时获取分页数据方法
     *
     * @return 获取分页数据
     */
    T getRows();

    /**
     * 设置分页数据方法
     *
     * @param obj 分页数据
     */
    void setRows(T obj);

    /**
     * 分页时获取数据总量方法
     *
     * @return 获取数据总量
     */
    Long getTotal();

    /**
     * 设置数据总量方法
     *
     * @param total 数据总量
     */
    void setTotal(Long total);
}
