package com.dx.test.business.test.controller.handler;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 处理器 Handler
 * <p>
 * Tips 通过实现 HttpRequestHandler 接口定义一个 Handler
 * Tips 这种 Handler 需要手动添加到 HandlerMapping 中才能跟请求对应上
 */
public class MyHttpRequestHandler implements HttpRequestHandler {

    /**
     * HttpRequestHandler 接口只有这一个方法
     * Tips 因为没有返回值,  HttpRequestHandler 类型的 Handler 并不涉及视图的查找和解析等过程
     * Tips 这种 Handler 适用于处理简单的 HTTP 请求, 例如一些不需要视图层的静态资源: JS, CSS, 图片等
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置输出的编码集
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        // 通知前端用什么格式解析
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        // HttpRequestHandler 类型的 Handler 直接通过 HttpServletResponse 向页面写入数据
        response.getWriter().println("这是一个 HttpRequestHandler 类型的 Handler 通过 HttpServletResponse 写入的内容");
    }
}
