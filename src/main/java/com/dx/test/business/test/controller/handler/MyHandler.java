package com.dx.test.business.test.controller.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器 Handler
 * Tips 这是一个自定义的 Handler, Spring 是允许任何类型的 Handler 的
 * Tips Handler 功能上属于 Model 层, 主要功能是处理数据模型及业务逻辑
 */
// Tips 当 Bean 的 name 以 "/" 开头时, 会根据名字自动建立请求映射, URL 就是 name, Bean 就是这个 URL 的处理器
@Component("/test/myHandler")
public class MyHandler {

    /**
     * Tips 定义一个处理方法, 这个方法可以随意定义参数和返回, 只要存在可以处理此方法的 HandlerAdapter 即可
     *
     * @param request HttpServletRequest
     * @return 此处返回的是视图名称
     */
    public String handle(HttpServletRequest request) {
        // 通过 request 传输一个数据
        request.setAttribute("test", "这是自定义的 Handler 通过 Request 传输的数据");

        // 返回指定的视图 TODO
        return "";
    }

}
