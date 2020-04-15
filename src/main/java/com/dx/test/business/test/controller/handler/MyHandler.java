package com.dx.test.business.test.controller.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器 Handler
 * 这是一个自定义的 Handler, Spring 是允许任何类型的 Handler 的
 */
// 当 Bean 的 name 以 "/" 开头时, 会根据名字自动建立请求映射, URL 就是 name, Bean 就是这个 URL 的处理器
@Component("/test/myHandler")
public class MyHandler {

    public void handle(HttpServletRequest request) {
        request.setAttribute("test", "这是自定义的 Handler 通过 Request 传输的数据");
    }

}
