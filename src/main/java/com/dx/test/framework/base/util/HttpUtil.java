package com.dx.test.framework.base.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

    /**
     * 判断是否为 ajax 请求
     *
     * @param request HttpServletRequest
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
