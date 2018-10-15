package org.yyx.domain;

import lombok.Data;
import org.yyx.util.http.BaseResponse;

/**
 * 响应实体类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/14-21:17
 */
@Data
public class ResponseEntity<T> implements BaseResponse<T> {
    /**
     * 响应码
     */
    private Long code;
    /**
     * 响应码的说明
     */
    private String msg;
    /**
     * 分页查询的总数据条数
     */
    private Long total;
    /**
     * 分页查询到的页面数据
     */
    private T rows;
    /**
     * 英文说明
     */
    private String description;
    /**
     * 返回数据
     */
    private T data;


    /**
     * 构造方法
     *
     * @param code 响应码
     * @param msg  响应说明
     */
    public ResponseEntity(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造方法
     */
    public ResponseEntity() {
    }

    /**
     * 构造方法
     *
     * @param code        响应码
     * @param msg         响应说明
     * @param description 响应英文说明
     */
    public ResponseEntity(Long code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }
}
