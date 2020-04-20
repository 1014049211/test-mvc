package com.dx.test.business.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示 @RequestMapping 用法的 Controller
 */
@Controller // Tips @Controller 标注的类叫控制器, 控制器 != 控制层, 控制器中的 Handler 功能上来说, 其实应该是 Model 层的
@RequestMapping("testRequestMapping") // Tips 注释在类上的, 在匹配请求时属性会跟方法上的合并
public class TestRequestMappingController {

    /**
     * 跳转到 requestMapping 视图
     * <p>
     * Tips path/value 属性不必在开头添加 "/" , Spring 检测到不是以 "/" 开头, 会自动添加
     */
    @RequestMapping("init")
    public String init() {

        /*
         * Tips 被 @RequestMapping 标注的方法会在 Spring 的加载过程中被包装成 HandlerMethod 类型的 Handler
         *  除了 @Controller 标注的类以外, @RequestMapping 在任何 Spring 的 Bean 中都可以生效
         *  SpringMVC 的分层, 并不是代码意义上的分层, 而是功能上, 代码上的规范写法是为了更直观的展示项目结构
         */

        // Tips 此处返回的 String 会跟 InternalResourceViewResolver 中配置的前缀和后缀组合, 作为 VIew 的 name
        return "test/requestMapping";
    }

    /**
     * path 属性演示1
     */
    @RequestMapping("testPath1")
    @ResponseBody // Tips 标识这个 Handler 返回的是数据而不是视图
    public String testPathAttribute1() {
        /*
         * Tips path 属性是 @RequestMapping 的主属性
         *  所谓主属性就是通过 @AliasFor 注解与 value 属性互为别名的属性
         * Tips 属性别名
         *  @AliasFor 是 Spring 的注解, 别名属性是 Spring 引入的概念
         *  互为别名的属性, 值是共用的, 一个属性的值变化, 另一个属性的值也会变化
         *  互为别名后, 两个属性不能同时在注解上显式的解除, 否则会编译报错
         * Tips value 属性
         *  在 Java 的注解定义中, value 属性是注解的默认属性
         *  正常情况下, 属性的赋值要使用 "属性名 = 属性值" 的形式
         *  如果只赋值 value 属性, 作为默认属性, 可以直接写属性值, 例如: @RequestMapping("testPath1")
         * Tips 为何 Spring 要使用别名机制
         *  1. 消除歧义
         *  开发中肯定是使用默认的 value 属性比较简洁方便, 因为不用写属性名
         *  但是这个 "value" 很容易产生歧义, 不同的人可以有很多理解, 但是 path 属性不一样, 大家都知道是请求路径
         *  2. 方便兼容老版本
         *  假如一开始就用 value 属性, 后来只加入 path 而不使用别名, 那么之前直接写属性值的方法就无法兼容
         *  假如以后废弃了 path 属性, 因为有别名机制, 重新指定一下就好, 不用修改代码
         */
        return "testPathAttribute1 处理了这个请求";
    }

    /**
     * path 属性演示2
     * Tips path/value 属性的值类型是数组, 注解中的数组传值是 {} 不是 [], 当只有一个值时也可以直接传值
     */
    @RequestMapping({"testPath2", "testPath3"})
    @ResponseBody
    public String testPathAttribute2() {
        return "testPathAttribute2 处理了这个请求";
    }

    @RequestMapping(path = "testPath1", method = RequestMethod.GET)
    @ResponseBody
    public String testPathAttribute3(){
        return "";
    }

}
