package com.dx.test.framework.base.advice;

import com.dx.test.framework.base.model.MyJsonView;
import com.dx.test.framework.base.util.HttpUtil;
import com.dx.test.framework.base.util.StringUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用 @ControllerAdvice 声明一个控制器建言
 * Tips @ControllerAdvice 标注的 Bean 可以视为一个超级控制器, 在这个 Bean 中可以对所有 @Controller 标注的 Bean 做一些统一处理
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * Tips @ExceptionHandler 标注的方法可以处理控制器中 @RequestMapping 标注的 Handler 抛出的异常
     *
     * @param request       HttpServletRequest
     * @param handlerMethod 抛出异常的 Handler, HandlerMethod 类型
     * @param exception     抛出的异常
     * @return ModelAndView Tips 返回值可以使任意类型或者 void, 并不强制要是 ModelAndView
     */
    @ExceptionHandler(Exception.class) // Tips 设置此方法处理的异常类型
//    @ResponseBody // Tips 如果不是返回 ModelAndView, 想在处理异常后继续返回数据, 还要像普通的 HandlerMethod 一样添加 @ResponseBody
    public ModelAndView exception(HttpServletRequest request, HandlerMethod handlerMethod, Exception exception) {

        /*
         * Tips 因为当前是写在 @ControllerAdvice 标注的 Bean 中, 所以作用范围是所有 @Controller 标注的 Bean
         *  如果写在普通的 @Controller 标注的 Bean, 则作用范围就是所在 Bean
         */

        System.out.println("[ExceptionHandler] " + handlerMethod.getMethod().getName()
                + " 发生了异常: " + exception.getMessage());

        // 设置返回的数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", Boolean.FALSE);

        // 返回 JSON 数据
        if (HttpUtil.isAjaxRequest(request)) {
            resultMap.put("message", exception.getMessage());
            resultMap.put("data", exception);
            return new ModelAndView(new MyJsonView(), resultMap);
        }
        // 返回 error 视图
        else {
            resultMap.put("message", StringUtil.printException(exception));
            return new ModelAndView("error", resultMap);
        }
    }

}
