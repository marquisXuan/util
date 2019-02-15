package org.yyx.xf.tool.web.domain.constant;

/**
 * 响应常量
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/14-21:45
 */
public class ResponseConstant {

    /**
     * 服务器异常
     */
    public static final String ERROR = "服务器异常，请联系管理员";
    /**
     * 服务器异常响应码
     */
    public static final Long ERROR_CODE = 99999L;
    /**
     * 服务器异常英文说明
     */
    public static final String ERROR_DESC = "server error";
    /**
     * 请求失败，原因未知
     */
    public static final String FAIL = "请求失败，原因未知";
    /**
     * 请求失败，原因未知
     */
    public static final Long FAIL_CODE = 99998L;
    /**
     * 请求失败，原因未知 英文说明
     */
    public static final String FAIL_DESC = "request failed";
    /**
     * 请求成功常量
     */
    public static final String SUCCESS = "请求成功";
    /**
     * 请求成功常量响应码
     */
    public static final Long SUCCESS_CODE = 0L;
    /**
     * 请求成功英文说明
     */
    public static final String SUCCESS_DESC = "request success";

    private ResponseConstant() {
    }
}
