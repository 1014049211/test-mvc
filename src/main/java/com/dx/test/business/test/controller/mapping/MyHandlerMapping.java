package com.dx.test.business.test.controller.mapping;

import com.dx.test.business.test.controller.handler.MyControllerHandler;
import com.dx.test.business.test.controller.handler.MyHttpRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理器映射 HandlerMapping
 * <p>
 * 注册 HandlerMapping 用于查找 Handler
 * 一个 web 请求必然包含一个 URL, HandlerMapping 就是通过 URL 来映射 Handler 的
 */
@Configuration
public class MyHandlerMapping {

    /**
     * 注册一个 HandlerMapping
     *
     * @return SimpleUrlHandlerMapping
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        // 用于绑定简单的请求与 Handler 的关系
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();

        // 键值对形式, key 是请求的 URL, value 是 Handler 实例
        Map<String, Object> urlMapping = new HashMap<>();
        // 绑定一个 HttpRequestHandler 类型的 Handler
        urlMapping.put("/test/myHttpRequestHandler", new MyHttpRequestHandler());
        // 绑定一个 Controller 类型的 Handler
        urlMapping.put("/test/myControllerHandler", new MyControllerHandler());

        // 将映射关系存入 HandlerMapping
        handlerMapping.setUrlMap(urlMapping);
        return handlerMapping;
    }

}
