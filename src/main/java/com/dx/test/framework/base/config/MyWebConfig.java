package com.dx.test.framework.base.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 通过实现 WebApplicationInitializer 接口来替代 web.xml
 * <p>
 * WebApplicationInitializer 虽然是在 Spring 的 jar 中, 但是需要 Servlet3.0+ 的支持
 * 底层的原理其实使用 Servlet 提供的 ServletContainerInitializer 接口
 * <p>
 * ServletContainerInitializer 使用 SPI 机制 TODO SPI 机制?
 * 在 web 容器启动时自动找到 META-INF/services 目录下以 ServletContainerInitializer 的全路径名称命名的文件
 * 它的内容为 ServletContainerInitializer 实现类的全路径
 * 根据实现类的全路径将实现类实例化, 这些实现类的内容就是用来代替 web.xml 的
 */
public class MyWebConfig implements WebApplicationInitializer {

    /**
     * 在 onStartup 中可以像 web.xml 一样配置 Servlet 和 Listener
     *
     * @param servletContext Servlet 容器
     */
    @Override
    public void onStartup(ServletContext servletContext) {

        // 注册 Spring 容器, 使用基于注解配置并支持 web 项目的 AnnotationConfigWebApplicationContext
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        // 设置主配置类
        // 此处可以传入多个, 但是没必要, 其他的配置类在传入的这个配置类里配置就可以
        applicationContext.register(MyMvcConfig.class);
        // 设置 web 容器
        applicationContext.setServletContext(servletContext);

        // 配置 SpringMVC 的核心类: DispatchServlet
        // 本质上 SpringMVC 就是一个功能强大的 Servlet
        ServletRegistration.Dynamic servlet = servletContext.addServlet(
                "dispatcher", new DispatcherServlet(applicationContext)
        );
        // 设置 DispatchServlet 处理所有请求
        servlet.addMapping("/");
        // 设置 DispatchServlet 随着 web 容器一同启动
        servlet.setLoadOnStartup(1);
    }
}
