package com.dx.test.business.test.controller.handler;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理器 Handler
 * <p>
 * Tips 通过实现 Controller 接口来定义一个 Handler
 * Tips 注意这个 Controller 是个接口不是 @Controller 注解
 * Tips Controller 类型的 Handler 需要 HandlerMapping 才能跟请求对应
 */
public class MyControllerHandler implements Controller {

    /**
     * Tips Controller 类型的 Handler 只有 handleRequest 一个方法
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return 返回值是 ModelAndView, 说明这种类型的 Handler 可以做数据和视图的处理
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {

        // ModelAdnView 中的数据是以键值对为基础形式
        Map<String, Object> param = new HashMap<>();
        param.put("name", "小明");
        param.put("sex", "人妖");

        return new ModelAndView("test/controllerHandler", param);
    }
}
