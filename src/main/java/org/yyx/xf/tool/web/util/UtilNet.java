package org.yyx.xf.tool.web.util;

import org.yyx.xf.tool.web.domain.constant.UtilNetConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * 网络工具类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2020/3/18 10:52 下午
 */
public class UtilNet {

    /**
     * 获取用户真实的IP地址
     *
     * @param request 请求
     * @return 用户的 IP 地址
     */
    public static String getUserIP(HttpServletRequest request) {
        // 优先取 X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = UtilNetConstant.getLOCAL_IP();
            }
        }
        if ("unknown".equalsIgnoreCase(ip)) {
            ip = UtilNetConstant.getLOCAL_IP();
            return ip;
        }
        int index = ip.indexOf(',');
        if (index >= 0) {
            ip = ip.substring(0, index);
        }
        return ip;
    }
}
