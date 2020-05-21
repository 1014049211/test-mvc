package com.dx.test.framework.base.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dx.test.framework.base.interceptor.MyInterceptor;
import com.dx.test.framework.base.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过实现 WebMvcConfigurer 来配置 SpringMVC
 * <p>
 * TIps 关于 WebMvcConfigurer 接口
 * WebMvcConfigurer 虽然是一个接口, 但是所有方法都是 default 的, 实现类可以不实现任何方法
 * 这种做法是为了简化配置: 只需重写那些要定制的方法, 而不必为了设置一个而重写所有方法
 * <p>
 * Tips 为何放弃抽象类 WebMvcConfigurerAdapter
 * 在 Spring5.0+ 之前的版本中, 配置 SpringMVC 需要继承抽象类 WebMvcConfigurerAdapter
 * 在 JDK1.8 给接口加入了 default 以后, Spring5.0+ 废弃了抽象类改为了接口
 * 所以 Spring5.0+ 至少需要 JDK1.8+ 的版本
 * <p>
 * Tips 抽象类和有了 default 的接口
 * 接口有了 default 以后并不能完全替代抽象类, 至少 JDK1.8 中不能
 * 首先接口所有的方法都是 public 的, 所有属性都是 public static final 的
 * 这导致接口没有可以保护的私有方法, 并且没有状态, 所谓没状态可以理解为无论你何时访问这个接口的属性, 都是一样的
 * 而抽象类可以设置私有的方法和属性
 */
@Configuration // Tips 使用这个注解标识当前类为 Spring 的配置类
@EnableWebMvc // Tips 在 SpringMVC 中, 不添加此注解, 重写 WebMvcConfigurer 的方法无效(如果是 SpringBoot 项目无需此注解)
@EnableAspectJAutoProxy // Tips 开启 Spring 对 AspectJ 语法的支持
@ComponentScan("com.dx") // Tips 告诉 Spring 都有哪些包需要扫描
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * FastJson 解析器
     */
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    /**
     * 注册视图解析器 InternalResourceViewResolver, 用于解析 JSP 视图
     * Tips InternalResourceViewResolver 是 SpringMVC 的默认配置, 但是需要显式的写出来配置一下前后缀
     * Tips 如果就是不想显式的写也可以, 前缀默认为 "/", 后缀默认是 ".jsp"
     *
     * @return InternalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置前缀
        viewResolver.setPrefix("/view/");
        // 设置后缀
        // Tips 其实这个后缀只是用于路径匹配文件, 对于解析文件意义不大
        //  当使用 InternalResourceViewResolver 时, 无论后缀是什么都按照 JSP 语法来解析
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * Tips 重写 addResourceHandlers 方法来配置静态资源映射
     * <p>
     * Tips 为了要配置静态资源映射
     * 程序的静态资源(如图片、JS、CSS 等) 需要直接访问, 并不需要 Handler 处理
     * 但是 DispatchServlet 会处理所有请求, 自然也包括静态资源的请求
     * 此时需要告诉 DispatchServlet 那些是静态资源
     * <p>
     * Tips 不使用静态资源映射如何处理静态资源
     * 设置 DispatchServlet 只处理特定后缀的请求也可以避免静态资源被拦截
     * 例如这样配置 DispatchServlet 要处理的请求: "/*.act"
     * 这样所有带 ".act" 后缀的请求才会被处理, 以 ".js"、".css" 等结尾的静态资源会直接放行
     * 个人认为每个请求后面都拼个后缀的方式不优雅
     *
     * @param registry 静态资源映射器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * Tips 重写 addViewControllers 方法可以添加快速的视图跳转映射
     *
     * @param registry 快捷视图跳转注册器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 根目录访问时跳转到主页
        registry.addViewController("/").setViewName("main");
    }

    /**
     * Tips 重写 addInterceptors 方法来注册拦截器
     *
     * @param registry Interceptor 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 可以添加多个, 添加顺序就是 preHandle 方法的执行顺序
        // 因为拦截器本身没有需要 Spring 增强的地方, 且只有这一次实例化, 所以直接 new 就可以了, 不用注入
        registry.addInterceptor(new MyInterceptor())
                // 添加需要拦截的 URL 请求路径, 支持 String 类型的可变长度参数列表和数组
                // 为空或省略此步骤表示拦截所有请求
                .addPathPatterns()
                // 添加不用拦截的 URL 请求路径, 支持 String 类型的可变长度参数列表和数组
                // 为空或省略此步骤表示没有需要特殊处理的请求
                // 此处不拦截跳转到主页的请求
                .excludePathPatterns("/");

        // 令牌拦截器
        registry.addInterceptor(new TokenInterceptor()).excludePathPatterns("/");

        // Tips 由于本项目的 RequestMappingHandlerMapping 是手动添加, 所以此方法里的拦截器不会作用于 HandlerMethod
        //  想生效需要手动向 RequestMappingHandlerMapping 中添加 MappedInterceptor 类型的拦截器
    }

    /**
     * Tips 重写 configureMessageConverters 方法注册自定义的解析器
     *
     * @param converters 解析器注册器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 注册 FastJson 解析器
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 用于处理 @RequestMapping 的 HandlerMapping
     * Tips 当配置了自定义的 HandlerMapping 以后, SpringMVC 将不再加载默认的 HandlerMapping
     * <p>
     * Tips SpringMVC 默认的 HandlerMapping
     * SpringMVC 默认的 HandlerMapping 配置在 DispatcherServlet.properties 中
     * key 是 org.springframework.web.servlet.HandlerMapping
     *
     * @return RequestMappingHandlerMapping
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 用于处理 @RequestMapping 的 HandlerAdapter
     * Tips 当配置了自定义的 HandlerAdapter 以后, SpringMVC 将不再加载默认的 HandlerAdapter
     * <p>
     * Tips SpringMVC 默认的 HandlerAdapter
     * SpringMVC 默认的 HandlerAdapter 配置在 DispatcherServlet.properties 中
     * key 是 org.springframework.web.servlet.HandlerAdapter
     *
     * @return RequestMappingHandlerAdapter
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(FastJsonHttpMessageConverter fastJsonHttpMessageConverter) {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();

        /*
         * Tips 此处手动设置一下 MessageConverter
         *  因为 RequestMappingHandlerAdapter 是手动添加的, 无参构造里会使用默认四种解析器
         *  此时 configureMessageConverters 中配置的 MessageConverter 对于 HandlerMethod 其实是白费
         *  所以要手动设置一下
         * TODO 想不通: 为什么自定义了 HandlerAdapter 和 HandlerMapping 以后 SpringMVC 就不加载默认的呢?
         *  而且加载方法是 final 的, 不让重写
         *  也没发现 SpringMVC 提供了其他的兼容办法
         *  这非常不合理
         */
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(fastJsonHttpMessageConverter);
        requestMappingHandlerAdapter.setMessageConverters(converters);

        return requestMappingHandlerAdapter;
    }

    /**
     * 注入 FastJson 解析器
     *
     * @param fastJsonHttpMessageConverter FastJsonHttpMessageConverter
     */
    @Autowired
    public void setFastJsonHttpMessageConverter(FastJsonHttpMessageConverter fastJsonHttpMessageConverter) {
        this.fastJsonHttpMessageConverter = fastJsonHttpMessageConverter;
    }
}
