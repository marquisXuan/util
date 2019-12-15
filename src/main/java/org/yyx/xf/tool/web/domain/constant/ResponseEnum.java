package org.yyx.xf.tool.web.domain.constant;

import lombok.Getter;

/**
 * 响应常量
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/14-21:45
 */
public enum ResponseEnum {

    /**
     * 请求成功时的响应码
     */
    success("000000000", "请求成功", "request success"),
    /**
     * 请求失败时的响应码
     */
    error("999999999", "服务器错误", "request failed");
    /**
     * 响应码
     */
    @Getter
    private String code;
    /**
     * 响应信息
     */
    @Getter
    private String msg;
    /**
     * 响应信息
     */
    @Getter
    private String des;

    /**
     * 枚举类构造
     *
     * @param code 响应码
     * @param msg  响应信息
     * @param des  响应信息
     */
    ResponseEnum(String code, String msg, String des) {
        this.code = code;
        this.msg = msg;
        this.des = des;
    }

}
