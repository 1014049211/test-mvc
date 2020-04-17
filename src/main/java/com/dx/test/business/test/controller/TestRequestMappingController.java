package com.dx.test.business.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示 @RequestMapping 用法
 */
@Controller // @Controller 标注的类叫控制器, 控制器 != 控制层, 控制器中的 Handler 功能上来说, 其实应该是 Model 层的
@RequestMapping("testRequestMapping") // 注释在类上的, 在匹配请求时属性会跟方法上的合并
public class TestRequestMappingController {

    /**
     * 跳转到 requestMapping 视图
     * <p>
     * 被 @RequestMapping 标注的方法会在 Spring 的加载过程中被包装成 HandlerMethod 类型的 Handler
     * 除了 @Controller 标注的类以外, @RequestMapping 在任何 Spring 的 Bean 中都可以生效
     * <p>
     * path/value 属性不必在开头添加 "/" , Spring 检测到不是以 "/" 开头, 会自动添加
     */
    @RequestMapping("init")
    public String init() {
        // 此处返回的 String 会跟 InternalResourceViewResolver 中配置的前缀和后缀组合, 作为 VIew 的 name
        return "test/requestMapping";
    }

}
