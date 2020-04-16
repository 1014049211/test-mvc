package com.dx.test.framework.base.config;

import com.dx.test.framework.base.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 通过实现 WebMvcConfigurer 来配置 SpringMVC
 * <p>
 * WebMvcConfigurer 虽然是一个接口, 但是所有方法都是 default 的, 实现类可以不实现任何方法
 * 这种做法是为了简化配置: 只需重写那些要定制的方法, 而不必为了设置一个而重写所有方法
 * <p>
 * 在 Spring5.0+ 之前的版本中, 配置 SpringMVC 需要继承抽象类 WebMvcConfigurerAdapter
 * 在 JDK1.8 给接口加入了 default 以后, Spring5.0+ 废弃了抽象类改为了接口
 * 所以 Spring5.0+ 至少需要 JDK1.8+ 的版本
 * <p>
 * 接口有了 default 以后并不能完全替代抽象类, 至少 JDK1.8 中不能
 * 首先接口所有的方法都是 public 的, 所有属性都是 public static final 的
 * 这导致接口没有可以保护的私有方法, 并且没有状态, 所谓没状态可以理解为无论你何时访问这个接口的属性, 都是一样的
 * 而抽象类可以设置私有的方法和属性
 */
@Configuration // 使用这个注解标识当前类为 Spring 的配置类
@EnableWebMvc // 在 SpringMVC 中, 不添加此注解, 重写 WebMvcConfigurer 时无效(如果是 SpringBoot 项目无需此注解)
@ComponentScan("com.dx") // 告诉 Spring 都有哪些包需要扫描
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 注册视图解析器 InternalResourceViewResolver, 用于解析 JSP 视图
     *
     * @return InternalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置前缀
        viewResolver.setPrefix("/view/");
        // 设置后缀
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * 重写 addInterceptors 方法来注册拦截器
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
                .excludePathPatterns();

    }

    /**
     * 用于处理 @RequestMapping 的 HandlerMapping
     * 当配置了自定义的 HandlerMapping 以后, SpringMVC 将不再加载默认的 HandlerMapping
     * 所以当需要使用的时候, 要自己注册
     * <p>
     * SpringMVC 默认的 HandlerMapping 配置在 DispatchServlet.properties 中
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
     * 当配置了自定义的 HandlerAdapter 以后, SpringMVC 将不再加载默认的 HandlerAdapter
     * 所以当需要使用的时候, 要自己注册
     * <p>
     * SpringMVC 默认的 HandlerAdapter 配置在 DispatchServlet.properties 中
     * key 是 org.springframework.web.servlet.HandlerAdapter
     *
     * @return RequestMappingHandlerAdapter
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }
}
