package com.dx.test.business.test.controller;

import com.dx.test.business.test.model.UserModel;
import com.dx.test.framework.base.model.ResultModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 参数获取演示类
 */
@Controller
@RequestMapping("testParam")
public class TestParamController {

    @RequestMapping("init")
    public String init() {
        return "test/param";
    }

    /**
     * 演示 REST 风格的请求路径获取参数的方法
     */
    @RequestMapping("testRest/{name}/{age}")
    @ResponseBody
    public String testRest(@PathVariable("name") String name, @PathVariable("age") Integer age) {

        /*
         * Tips {} 是一种路径匹配, 完整格式是 {fileName:regex}, regex 是正则表达式, 用于匹配路径元素
         *  匹配到的内容会赋值给 fileName, SpringMVC 对于 REST 风格的支持就基于此
         *
         * Tips /{name}/ 这是一种简写, 意思就是将两个 "/" 之间的内容赋值给 name, 要获取 name 就需要使用 @PathVariable
         *
         * Tips @PathVariable 的 required 属性用于决定找不到变量时是否报错, 默认是 true, 代表报错
         *  设置为 false 时, 找不到会赋予变量 null 值, 所以使用 @PathVariable 时基本类型参数最好替换为包装类型
         *
         * Tips 作为接受网络请求的方法, 参数本就不应该使用基本类型, 因为基本类型不能接收 null 值
         *  网络请求不可控, 没法约定编程, null 的处理应该在业务中做, 不应该方法直接报错
         */

        return "name = " + name + ", age = " + age;
    }

    /**
     * 演示 @RequestParam
     */
    @RequestMapping(path = "testRequestParam", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String testRequestParam(@RequestParam("name") String name, Integer age,
                                   @RequestParam(value = "gender", defaultValue = "人妖") String gender) {

        /*
         * Tips 注解 @RequestParam 获取数据的原理就是 request.getParameter
         *
         * Tips 注解了 @RequestParam 的参数默认必须有, 可以设置属性 required 为 false 来允许空值
         *  还可以使用 default 来设置当参数为空时默认使用什么值
         *  如果设置了 default, required 就相当于 false
         *
         * Tips 声明在 HandlerMethod 上的参数, 如果没有任何注解, 那么默认的隐式添加 @RequestParam 注解
         *  也就是没有任何注解的参数就是通过 request.getParameter 方法获取的, key 就是 参数名
         *  隐式添加的 @RequestParam, required 属性为 false, 允许参数为空
         */

        return "name = " + name + ", age = " + age + ", gender = " + gender;
    }

    /**
     * 演示 @RequestBody
     * Tips 同一个 Handler 的参数中, @RequestBody 只能使用一次, 因为 Request 中的数据流是单向的, 只能读取一次
     */
    @RequestMapping("testRequestBody")
    @ResponseBody
    public ResultModel testRequestBody(@RequestBody UserModel userModel) {
        return new ResultModel(userModel);
    }

    /**
     * 不使用 @RequestBody 接收复杂对象
     * Tips 没有 @RequestBody 注解的复杂对象在接收数据时, 只能做基本的类型转换, 是无法将 String 转为 Enum 的
     */
    @RequestMapping("testComplexity")
    @ResponseBody
    public ResultModel testComplexity(UserModel userModel) {
        return new ResultModel(userModel);
    }

}
