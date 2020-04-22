package com.dx.test.framework.base.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 Interceptor
 * <p>
 * Tips 如何定义 Interceptor
 * 实现 HandlerInterceptor 接口可以定义一个 Interceptor
 * HandlerInterceptor 所有的方法都是 default 的, 实现类只需要重写自己需要的方法
 * <p>
 * Tips Interceptor 的执行时机
 * Spring 根据 HandlerMapping 查找到 Handler 时, 并不是直接返回 Handler 实例, 而是返回 Handler 执行链: HandlerExecutionChain
 * 这个执行链中封装了所有作用在当前 Handler 上的 Interceptor
 * <p>
 * Tips Interceptor 本身并不需要 Spring 在加载过程中做加强和改变, 添加到 Spring 也是通过注册器, 所以并不用标识为 Spring 的 Bean
 * <p>
 * Tips Interceptor 与过滤器(Filter)的区别
 * 1. 本质区别:
 * Filter 是 Servlet 标准组件, 而 Interceptor 属于 Spring
 * Interceptor 可以获得 SpringMVC 提供的 Handler 和 ModelAndView 等组件, Filter 不能
 * 2. 作用区别:
 * Filter 针对的是 URL, URL 匹配以后就会执行对应的 Filter
 * Interceptor 针对的是 Handler, 要先找到 Handler, 再找到作用于 Handler 上的 Interceptor
 * 3. 执行时机区别:
 * Filter 在进入 Servlet 之前被调用
 * Interceptor 作为 SpringMVC 的组件是在 DispatchServlet 中调用的, 也就是进入 Servlet 以后被调用
 */
public class MyInterceptor implements HandlerInterceptor {

    // 打印消息时使用的前缀
    private static final String PREFIX = "[MyInterceptor] ";

    /**
     * 前置方法, 在 Handler 执行前调用
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object 类型的 Handler 参数, 可以接收任何类型的 Handler
     * @return 用于表示是否可以通过当前 Interceptor: true 代表通过,  false 不通过
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(PREFIX + request.getRequestURI() + " 进入 preHandle 方法");

        // 将当前时间放入 request 中用于统计 Handler 的执行时间
        request.setAttribute("MyInterceptor_startTime", System.currentTimeMillis());

        /*
         * Tips 当前置方法返回 true 时, 表示通过当前 Interceptor , 并进入下一个 Interceptor 的 preHandle 方法
         *  直到所有作用在当前 Handler 上的 Interceptor 的 preHandle 方法都返回 true 时, 开始调用 Handler
         *  当前置方法返回 false 时, 直接中断请求
         */
        return true;
    }

    /**
     * 后置方法, 在 Handler 执行完以后调用
     * Tips 如果 Handler 抛出了异常, postHandle 方法会被跳过
     * Tips Interceptor 的执行顺序是栈模式, 先进后出, 后进先出, 所以 postHandle 的执行顺序是跟 preHandle 相反的
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param handler      刚刚执行完的 Handler
     * @param modelAndView Handler 的处理结果, 可能为空, 因为例如 HttpRequestHandler 这种类型的 Handler 并没有返回值
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        System.out.println(PREFIX + request.getRequestURI() + " 进入 postHandle 方法");

        // 记录每次请求的处理用时
        this.recordUsedTime(request);
    }

    /**
     * afterCompletion 方法跟 postHandle 一样是在 Handler 执行后调用
     * Tips afterCompletion 与 postHandle 区别在于, 即便处理器抛出了异常, afterCompletion 方法也依然会被执行, 并且会获得发生的异常
     * Tips afterCompletion 就像 try-catch-finally 中的 finally
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  刚刚执行完的 Handler
     * @param e        Handler 抛出的异常, 如果 Handler 没有抛出异常, 此参数为空
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception e) {
        // 当 Handler 抛出了异常时
        if (e != null) {
            // 记录异常 TODO 此处可以考虑发邮件或者录入数据库
            System.out.println(PREFIX + request.getRequestURI() + " 因发生异常而结束: " + e.getMessage());
            // 记录用时: 如果发生了异常, 不会调用 postHandle 方法, 所以要在这里记录
            this.recordUsedTime(request);
        }

    }

    /**
     * 记录每次请求的处理用时
     *
     * @param request HttpServletRequest
     */
    private void recordUsedTime(HttpServletRequest request) {

        // 提取开始处理请求的时间
        Long startTime;
        try {
            startTime = (Long) request.getAttribute("MyInterceptor_startTime");
        } catch (Exception e) {
            startTime = null;
        }

        // 请求路径拼装到前缀中
        String prefix = PREFIX + request.getRequestURI();

        // 计算用时
        if (startTime == null) {
            System.out.println(prefix + " 没有获取到处理的开始时间, 无法计算!");
        } else {
            // 计算用时
            long usedTime = System.currentTimeMillis() - startTime;
            // 输出
            System.out.println(prefix + " 的处理时间为: " + usedTime + " 毫秒");
            // 处理时间超过 10 秒判定为处理缓慢
            if (usedTime > 10000) {
                // TODO 此处可以考虑发邮件或者录入数据库
                System.out.println(prefix + " 的处理异常缓慢!");
            }
        }
    }


}
