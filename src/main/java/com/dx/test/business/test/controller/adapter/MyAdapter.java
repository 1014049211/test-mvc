package com.dx.test.business.test.controller.adapter;

import com.dx.test.business.test.controller.handler.MyHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器适配器 HandlerAdapter
 * <p>
 * 实现 HandlerAdapter 可以自定义一个适配器
 */
@Component
public class MyAdapter implements HandlerAdapter {

    /**
     * 用于判断 HandlerAdapter 是否能处理传入的 Handler
     *
     * @param handler 因为 Handler 可以是任意类型, 所以此处使用 Object 声明参数
     * @return 是否可以处理当前 Handler: true 可以, false 不可以
     */
    @Override
    public boolean supports(Object handler) {
        // 当前 HandlerAdapter 用于处理自定义的 Handler
        return handler instanceof MyHandler;
    }

    /**
     * HandlerAdapter 的核心方法, 用于调用 Handler
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  处理器
     * @return ModelAndView
     */
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 直接强转, 因为在 supports 方法中已经确认过类型了
        MyHandler myHandler = (MyHandler) handler;
        // 调用 Handler 处理业务, 这里可以是任意方法或业务逻辑
        myHandler.handle(request);

        // 指定跳转的视图
        return new ModelAndView("");
    }

    /**
     * 提供最后修改时间
     *
     * @param request HttpServletRequest
     * @param handler 处理器
     * @return 最后修改时间
     */
    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        // 这个方法没什么用, 直接返回 -1 表示不支持提供最后修改时间的功能即可
        return -1;
    }
}
