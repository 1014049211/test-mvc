package com.dx.test.business.test.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示 @RequestMapping 用法的 Service
 * Tips @RequestMapping 在任何 Spring 的 Bean 中都可以生效
 * Tips SpringMVC 的分层, 并不是代码意义上的分层, 而是功能上, 代码上的规范写法是为了更直观的展示项目结构
 */
@Service
@RequestMapping("testRequestMapping")
public class TestRequestMappingService {

    /**
     * 这是一个放在 service 中的, 由 @RequestMapping 标注的 Handler
     */
    @RequestMapping("inService")
    @ResponseBody
    public String testRequestMapping() {
        return "这是放在 service 中的 @RequestMapping";
    }

}
