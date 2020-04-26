package com.dx.test.business.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 演示 @RequestMapping 用法的 Controller
 */
@Controller // Tips @Controller 标注的类叫控制器, 控制器 != 控制层, 控制器中的 Handler 功能上来说, 其实应该是 Model 层的
@RequestMapping("testRequestMapping") // Tips 注释在类上的 @RequestMapping, 在匹配请求时属性会跟方法上的合并
public class TestRequestMappingController {

    /**
     * 跳转到 requestMapping 视图
     * Tips 被 @RequestMapping 标注的方法会在 Spring 的加载过程中被包装成 HandlerMethod 类型的 Handler
     * <p>
     * Tips path/value 属性不必在开头添加 "/" , Spring 检测到不是以 "/" 开头, 会自动添加
     */
    @RequestMapping("init")
    public String init() {
        // Tips 此处返回的 String 会跟 InternalResourceViewResolver 中配置的前缀和后缀组合, 作为 VIew 的 name
        return "test/requestMapping";
    }

    /**
     * path 属性演示1 - 基本用法
     */
    @RequestMapping("testPath1")
    @ResponseBody // Tips 标识这个 Handler 返回的是数据而不是视图
    public String testAttribute1() {
        /*
         * Tips path 属性是 @RequestMapping 的主属性
         *  所谓主属性就是通过 @AliasFor 注解与 value 属性互为别名的属性
         *
         * Tips 属性别名
         *  @AliasFor 是 Spring 的注解, 别名属性是 Spring 引入的概念
         *  互为别名的属性, 值是共用的, 一个属性的值变化, 另一个属性的值也会变化
         *  互为别名后, 两个属性不能同时在注解上显式的解除, 否则会编译报错
         *
         * Tips value 属性
         *  在 Java 的注解定义中, value 属性是注解的默认属性
         *  正常情况下, 属性的赋值要使用 "属性名 = 属性值" 的形式
         *  如果只赋值 value 属性, 作为默认属性, 可以直接写属性值, 例如: @RequestMapping("testPath1")
         *
         * Tips 为何 Spring 要使用别名机制
         *  1. 消除歧义
         *  开发中肯定是使用默认的 value 属性比较简洁方便, 因为不用写属性名
         *  但是这个 "value" 很容易产生歧义, 不同的人可以有很多理解, 但是 path 属性不一样, 大家都知道是请求路径
         *  2. 方便兼容老版本
         *  假如一开始就用 value 属性, 后来只加入 path 而不使用别名, 那么之前直接写属性值的方法就无法兼容
         *  假如以后废弃了 path 属性, 因为有别名机制, 重新指定一下就好, 不用修改代码
         */
        return "testAttribute1 处理了这个请求";
    }

    /**
     * path 属性演示2 - 数组值
     * Tips path/value 属性的值类型是数组, 注解中的数组传值是 {} 不是 [], 当只有一个值时也可以直接传值
     * Tips 数组中元素的关系是 "或"
     */
    @RequestMapping({"testPath2", "testPath3"})
    @ResponseBody
    public String testAttribute2() {
        return "testAttribute2 处理了这个请求";
    }

    /**
     * method 属性演示
     */
    @RequestMapping(path = "testPath1", method = RequestMethod.GET)
    @ResponseBody
    public String testAttribute4() {

        /*
         * Tips method 属性值是数组格式, 支持单个值直接传, 多个值之间匹配规则是 "或"
         *
         * Tips 跟 testAttribute1 相同的 path 值, 此时通过 method 来判断
         *  testAttribute1 没有 method 属性, 相当于匹配所有请求, 包括 GET 和 POST
         *  在 @RequestMapping 中所有属性的匹配规则都是越精确的匹配优先越高
         *  所以 GET 请求会由当前方法处理
         */

        return "testAttribute4 处理了这个请求";
    }

    /**
     * params 属性演示1
     * Tips 必须有 name 这个参数
     * Tips 与 testAttribute8 冲突, 加 method 加进一步区分
     */
    @RequestMapping(path = "testParams", params = "name", method = RequestMethod.GET)
//    @GetMapping(path = "testParams", params = "name")
    @ResponseBody
    public String testAttribute5() {
        return "testAttribute5 处理了这个请求";
    }

    /**
     * params 属性演示2
     * Tips 必须没有 name 这个参数
     * Tips 与 testAttribute7 冲突, 加 method 加进一步区分
     */
    @RequestMapping(path = "testParams", params = "!name", method = RequestMethod.GET)
//    @GetMapping(path = "testParams", params = "!name")
    @ResponseBody
    public String testAttribute6() {
        return "testAttribute6 处理了这个请求";
    }

    /**
     * params 属性演示3
     * Tips 必须有 name 这个参数, 且值必须是 "小明"
     */
    @RequestMapping(path = "testParams", params = "name=小明")
    @ResponseBody
    public String testAttribute7() {
        return "testAttribute7 处理了这个请求";
    }

    /**
     * params 属性演示4
     * Tips 必须有 name 这个参数, 且值必须不是 "小明"
     */
    @RequestMapping(path = "testParams", params = "name!=小明")
    @ResponseBody
    public String testAttribute8() {
        return "testAttribute8 处理了这个请求";
    }

}
