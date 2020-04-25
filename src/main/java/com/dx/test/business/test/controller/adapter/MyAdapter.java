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
 * Tips 实现 HandlerAdapter 可以自定义一个适配器
 * <p>
 * Tips 为何需要 HandlerAdapter
 * SpringMVC 通过 HandlerMapping 找到 Handler 以后并不是直接调用
 * 因为 Handler 可以是任意类型, 所以需要 HandlerAdapter 这个接口来定义 Handler 的执行标准
 * <p>
 * Tips HandlerAdapter 如何工作
 * SpringMVC 会在启动时将所有 HandlerAdapter 存储起来
 * 当需要执行 Handler 时, 就遍历所有 HandlerAdapter, 通过 supports 方法判断是否可以执行这个 Handler
 * 当 supports 返回 true 时, 就调用 handle 方法来执行 Handler
 * SpringMVC 本身并不关心执行过程, 也不用知道执行规则, 只需提供数据并接收指定类型的返回值
 * 这种设计不仅对 Handler 进行了解耦, 还给与开发者极大的自由
 * 这也是接口的一个重要意义和作用
 */
@Component // Tips HandlerAdapter 需要注册为 Spring 的 Bean 才能在执行 Handler 时被找到
public class MyAdapter implements HandlerAdapter {

    /**
     * 用于判断 HandlerAdapter 是否能处理传入的 Handler
     *
     * @param handler Tips 因为 Handler 可以是任意类型, 所以此处使用 Object 声明参数
     * @return 是否可以处理当前 Handler: true 可以, false 不可以
     */
    @Override
    public boolean supports(Object handler) {
        // 当前 HandlerAdapter 用于处理自定义的 Handler
        return handler instanceof MyHandler;
    }

    /**
     * 调用 Handler
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  处理器
     * @return ModelAndView
     */
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        /*
         * Tips HandlerAdapter 的核心方法, 用于真正调用 Handler 来处理业务逻辑
         *  一般来说 HandlerAdapter 作为一个介质组件, 不会进行业务逻辑的处理
         *  主要的任务就是根据 Handler 声明的参数提供数据, 并将 Handler 返回的处理结果封装为 ModelAndView
         */

        // 直接强转, 因为在 supports 方法中已经确认过类型了
        MyHandler myHandler = (MyHandler) handler;

        // 提供方法所需要的参数, 执行业务逻辑, 获得返回的视图名称
        // Tips SpringMVC 默认的组件中, 根据 Handler 参数类型拼凑数据的任务是由 ArgumentResolver 来解决的
        // Tips HandlerMethod 对应的 ArgumentResolver 是 HandlerMethodArgumentResolver
        // TODO ArgumentResolver
        String viewName = myHandler.handle(request);

        // 通过返回 ModelAndView 来告诉 SpringMVC 要跳转的视图和需要传递的数据
        // ModelAndView 支持 Map 类型的数据
        // 此处除了 Request 中已经存在的数据以外, 没有额外要传递的, 所以使用的是视图名构造方法
        return new ModelAndView(viewName);
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
