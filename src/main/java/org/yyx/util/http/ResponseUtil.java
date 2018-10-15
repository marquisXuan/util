package org.yyx.util.http;

import org.yyx.domain.ResponseEntity;

import static org.yyx.constant.ResponseConstant.ERROR;
import static org.yyx.constant.ResponseConstant.ERROR_CODE;
import static org.yyx.constant.ResponseConstant.ERROR_DESC;
import static org.yyx.constant.ResponseConstant.SUCCESS;
import static org.yyx.constant.ResponseConstant.SUCCESS_CODE;
import static org.yyx.constant.ResponseConstant.SUCCESS_DESC;

/**
 * Http响应工具类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/10/14-21:13
 */
public class ResponseUtil {
    /**
     * 私有构造
     */
    private ResponseUtil() {
    }

    /**
     * 服务器异常
     *
     * @param <T> 泛型
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> error() {
        return error(ERROR_CODE, ERROR, ERROR_DESC);
    }

    /**
     * 服务器异常
     *
     * @param <T>         泛型
     * @param code        响应码
     * @param message     响应信息中文说明
     * @param descrpition 响应信息英文说明
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> error(Long code, String message, String descrpition) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(code);
        responseEntity.setDescription(descrpition);
        responseEntity.setMsg(message);
        return responseEntity;
    }

    /**
     * 服务器异常
     *
     * @param <T>          泛型
     * @param baseResponse 响应结构
     * @return 封装的数据结构
     */
    public static <T> ResponseEntity<T> error(BaseResponse<T> baseResponse) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(baseResponse.getCode());
        responseEntity.setDescription(baseResponse.getDescription());
        responseEntity.setMsg(baseResponse.getMsg());
        responseEntity.setTotal(baseResponse.getTotal());
        responseEntity.setRows(baseResponse.getRows());
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
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(SUCCESS_CODE);
        responseEntity.setDescription(SUCCESS_DESC);
        responseEntity.setMsg(SUCCESS);
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
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(SUCCESS_CODE);
        responseEntity.setDescription(SUCCESS_DESC);
        responseEntity.setMsg(SUCCESS);
        responseEntity.setData(responseData);
        return responseEntity;
    }
}
