package org.yyx.xf.tool.web.util;

import org.yyx.xf.tool.web.domain.constant.ResponseEnum;
import org.yyx.xf.tool.web.domain.entity.BaseResponse;
import org.yyx.xf.tool.web.domain.entity.ResponseEntity;

/**
 * Http响应工具类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/14-21:13
 */
public class UtilResponse {
    /**
     * 私有构造
     */
    private UtilResponse() {
    }

    /**
     * 服务器异常
     *
     * @param <T> 泛型
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> error() {
        return generateErrorEntity();
    }

    /**
     * 服务器异常
     *
     * @param <T>         泛型
     * @param code        响应码
     * @param message     响应信息中文说明
     * @param description 响应信息英文说明
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> error(String code, String message, String description) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(code);
        responseEntity.setDescription(description);
        responseEntity.setMsg(message);
        return responseEntity;
    }

    /**
     * 服务器异常
     * 失败时不予前台返回数据.
     *
     * @param <T>          泛型
     * @param baseResponse 响应结构
     * @return 封装的数据结构
     */
    @Deprecated
    public static <T> ResponseEntity<T> error(BaseResponse<T> baseResponse) {
        ResponseEntity<T> responseEntity = generateErrorEntity();
        responseEntity.setTotal(baseResponse.getTotal());
        responseEntity.setRows(baseResponse.getRows());
        responseEntity.setData(baseResponse.getData());
        return responseEntity;
    }

    /**
     * 请求分页接口成功时返回的数据结构
     *
     * @param responseData 返回页面的分页数据
     * @param <T>          泛型
     * @param total        分页总条数
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> success(T responseData, Long total) {
        ResponseEntity<T> responseEntity = generateSuccessEntity();
        responseEntity.setTotal(total);
        responseEntity.setRows(responseData);
        return responseEntity;
    }

    /**
     * 请求成功时返回的数据结构
     *
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> success() {
        return success(null);
    }

    /**
     * 请求成功时返回的数据结构
     *
     * @param responseData 返回页面的数据
     * @param <T>          泛型
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> success(T responseData) {
        ResponseEntity<T> responseEntity = generateSuccessEntity();
        responseEntity.setData(responseData);
        return responseEntity;
    }

    /**
     * 私有方法,生成成功响应
     */
    private static <T> ResponseEntity<T> generateSuccessEntity() {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(ResponseEnum.success.getCode());
        responseEntity.setDescription(ResponseEnum.success.getDes());
        responseEntity.setMsg(ResponseEnum.success.getMsg());
        return responseEntity;
    }

    /**
     * 私有方法,生成失败响应
     */
    private static <T> ResponseEntity<T> generateErrorEntity() {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(ResponseEnum.error.getCode());
        responseEntity.setDescription(ResponseEnum.error.getDes());
        responseEntity.setMsg(ResponseEnum.error.getMsg());
        return responseEntity;
    }

}
