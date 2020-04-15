package com.dx.test.business.test.controller.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器 Handler
 * 这是一个自定义的 Handler, Spring 是允许任何类型的 Handler 的
 */
@Component("/test/myHandler")
// 当 Bean 的 name 以 "/" 开头时, 会根据名字自动建立映射
public class MyHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response) {

    }

}
