package com.dx.test.framework.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.dx.test.framework.base.constant.ReturnCodeEnum;
import com.dx.test.framework.base.model.ResultModel;
import com.dx.test.framework.base.util.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * 令牌拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {

    /*
     * Tips 接口的幂等性
     *  幂等性就是要求接口在 "增删改查" 时, 如果因为网咯延迟或者错误操作等原因造成多次重复请求, 能保证结果保持一致
     *  "查" 具有天然的幂等性, 因为查询请求不会对数据产生影响
     *  "删" 的幂等性仅限于数据, 因为数据一旦删除, 第二次删除的操作不会对结果产生变化, 但是对于请求本身来说, 可能会返回错误提示
     *  "改" 具有局限的幂等性, 定值更新每次都更成一个值, 不会影响数据结果, 但是增减更新会影响
     *  "增" 完全没有幂等性, 重复的请求每次都会新插入数据
     *
     * Tips 如何保证幂等性
     *  1. 业务限制
     *  2. 令牌(Token)
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取 session 提取令牌
        // Tips 令牌存入 session 时, 可以使用请求地址作为键, 此处为了省事就用 "token" 了
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("token");
        // 如果 session 中还没有令牌, 是第一次请求, 生成一个令牌
        if (StringUtil.isEmpty(sessionToken)) {
            session.setAttribute("token", "1");
        } else {
            // 重复请求
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            response.getWriter().write(JSON.toJSONString(
                    new ResultModel(ReturnCodeEnum.FAIL, "操作进行中请稍再试!"))
            );
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 无论请求是否成功, 都删除令牌
        request.getSession().removeAttribute("token");
    }
}
