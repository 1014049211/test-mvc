package com.dx.test.framework.base.model;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 自定义视图, 用于返回 json 数据, 有了这个视图, 就可以在同一个方法里既可以返回页面也可以返回数据了
 * Tips 实现 View 接口的类就可以做为 SpringMVC 的视图
 */
public class MyJsonView implements View {

    /**
     * 实现类只需要实现这一个方法
     *
     * @param model    传入的数据
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置 response 用什么格式输出
        response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        // 此处直接将数据转为 json 字符串输出到前端
        response.getWriter().write(JSON.toJSONString(model));
    }

    /**
     * 告诉前端用什么格式解析
     */
    @Override
    public String getContentType() {
        return "text/html;charset=UTF-8";
    }
}
