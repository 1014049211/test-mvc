package com.dx.test.framework.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 切面演示
 * Tips 想让 Spring 支持 AspectJ 的语法, 除了本类的配置以外, 还要在配置类上添加 @EnableAspectJAutoProxy 注解
 */
@Aspect // Tips 使用 @Aspect 将一个类声明为切面
@Component // Tips 想让 Spring 感知到这个切面, 还要注册为 Spring 的 Bean
public class MyAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * TIps 面向切面(AOP)
     *  1. AOP 是一种概念, Spring 只是实现了这种概念, 并不是发明和创造了 AOP
     *   在传统的 OOP(面向对象) 开发中, 代码的逻辑是自上而下的线性执行, 在执行过程中, 会产生横切性问题
     *   而这些横切性问题往往与业务逻辑关系不大, AOP 就是用来剥离代码中与业务无关的横切性问题
     *  2. 什么是横切性问题:
     *   当需要为多个不具有继承关系的对象引入同一个公共行为时(例如日志、安全检测等)
     *   在 OOP 的线性执行中只能在每个对象中引用这个公共行为, 产生大量的重复代码, 不便于统一维护
     *  3. AOP 是如何解决横切性问题的:
     *   与 OOP 中调用一个具备所需公共行为的对象来做这件事不同, AOP 是让程序的某一类行为去触发这个公共行为
     *   就像 JS 中的响应事件一样(例如: onclick、onchange等), AOP 可以在某一类行为的开始前、结束后或发生异常时, 自动去执行指定的公共行为
     *  4. 举例说明 Spring 中的 AOP 应用:
     *   Spring 的事务控制是基于 AOP 的
     *   在 Spring 中, 想让一个方法内的数据库操作保持一个事务, 只需要在方法上标注 @Transactional 注解
     *   Spring 依据 @Transactional 注解上的属性设置自动处理事务:
     *   方法开始前自动开始事务, 执行结束后自动提交事务, 发生异常时自动回滚事务
     *  5. Spring 是怎么做到 AOP 的: Spring 使用代理来实现 AOP
     *  6. 代理原理演示在本项目的: testJava.JDKProxy.Testing
     */

    /**
     * 一个基于 execution 语法的切点
     * <p>
     * Tips 使用 @Pointcut 可以将一个方法定义为一个切点, 不需要参数和返回值, 加了也没用, 此方法并不会被调用, 它只是注解的承载体
     * <p>
     * TIps 所谓切点, 可以直接理解为 jQuery 的选择器, 用于确定切面的作用范围
     */
    @Pointcut("execution(* com.dx.test.business.test.controller.*.*(..))")
    public void executionPointcut() {
        /*
         * Tips execution 是根据路径和方法定义来确定切面的作用范围
         *  在当前注解属性中:
         *  第一个 * 代表任意返回值
         *  第二个 * 代表包下任意类
         *  第三个 * 代表类中任意方法
         *  括号中的两个 . 代表方法的任意参数
         *  整体意思就是: 切点为 com.dx.test.business.test.controller 下的所有方法
         */
    }

    /**
     * 一个基于 @annotation 语法的切点
     * <p>
     * Tips @annotation 是通过注解来确定切面的作用范围, 括号里要写全路径
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void annotationPointcut() {
        // Tips 当前切点的作用范围是所有注解了 @RequestMapping 注解的方法
        // Tips 所有切面最终的作用单位都是方法, 无论什么语法, 切面是对方法的增强
    }

    /**
     * Tips 使用 @Before 定义一个前置方法, 属性值为这个前置方法的作用范围
     *
     * @param point 切点作用范围内, 当前调用的方法的包装
     */
    @Before("executionPointcut() && annotationPointcut()")
    public void before(JoinPoint point) {
        logger.info("[AOP Before] " + point.getClass().getSimpleName()
                + "#" + point.getSignature().getName() + "开始调用了");
    }

    /**
     * Tips 使用 @After 定义一个后置方法, 属性值为这个前置方法的作用范围
     *
     * @param point 切点作用范围内, 当前调用的方法的包装
     */
    @After("executionPointcut() && annotationPointcut()")
    public void after(JoinPoint point) {
        logger.info("[AOP After] " + point.getClass().getSimpleName()
                + "#" + point.getSignature().getName() + "调用结束了");
    }

    /**
     * Tips 使用 @AfterReturning 定义切点的返回值处理方法
     *
     * @param point  切点作用范围内, 当前调用的方法的包装
     * @param result 方法的返回值
     */
    @AfterReturning(value = "executionPointcut() && annotationPointcut()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {
        // Tips returning 定义返回值传给哪个参数, 此处传给 result
        // Tips result 的类型设置为 Object 代表可以接收任意类型的返回值, 如果设置为 Integer, 那么只能处理返回值为 Integer 类型的情况
        String message = "[AOP AfterReturning] " + point.getClass().getSimpleName() +
                "#" + point.getSignature().getName();
        if (result == null) {
            logger.info(message + " 没有返回值");
        } else {
            logger.info(message + " 的返回值为: " + result.toString());
        }
    }

    /**
     * Tips 使用 @AfterThrowing 定义切点的异常处理方法
     *
     * @param point 切点作用范围内, 当前调用的方法的包装
     * @param e     方法抛出的异常
     */
    @AfterThrowing(value = "executionPointcut() && annotationPointcut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        // Tips throwing 定义将异常传给哪个参数, 此处传给 e
        // Tips e 的类型设置为 Exception 代表所有异常都会进入这个方法来处理. 如果指定异常类型, 只有指定类型的异常会传入
        // Tips 传入此处的异常并不是被 catch, 依然会抛出, 只是抛出之前会调用这个方法
        logger.error("[AOP AfterThrowing] " + point.getClass().getSimpleName() +
                "#" + point.getSignature().getName() + " 发生了异常: " + e.getMessage(), e);
    }

    /**
     * Tips 使用 @Around 定义切点的环绕方法, 集成了前置、后置、返回值、异常四种方法
     *
     * @param point 切点作用范围内, 当前调用的方法的包装
     * @return 切点执行以后的返回值
     * @throws Throwable 此处直接抛出切点的异常, 交由其他组件处理, 不在此处处理
     */
    @Around("executionPointcut() && annotationPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        String message = "[AOP Around] " + point.getClass().getSimpleName() +
                "#" + point.getSignature().getName();

        logger.info(message + " start");

        // Tips 在 Around 中, 可以修改切点的执行参数和返回值 ,并且可以在此处理切点的异常
        Object[] args = point.getArgs();
        // 没有参数时返回的是空数组, 不会返回 null
        // Tips 编写程序时, 返回数组或集合的方法都应该尽量避免返回 null, 可以省掉很多麻烦
        if (args.length == 0) {
            logger.info(message + " 没有参数");
        } else {
            logger.info(message + " 参数为:");
            for (Object arg : args) {
                logger.info(String.valueOf(arg));
            }
        }

        // 获取当前系统时间
        long startTime = System.currentTimeMillis();

        // Tips 在 Around 中, 可以通过 ProceedingJoinPoint 的 proceed 方法使切点继续执行并获得返回值
        Object result;
        try {
            // Tips 调用原方法直接使用 proceed(), 方法会按照原有的参数执行
            //  如果想定制参数, 需要把处理完的参数包装为数组类型, 作为 proceed 方法的参数传入: proceed(args[])
            result = point.proceed();
        } finally {
            // 获取当前系统时间
            long endTime = System.currentTimeMillis();

            // 打印执行时间
            logger.info(
                    "[AOP Around] " + point.getClass().getSimpleName() +
                            "#" + point.getSignature().getName() + " 方法的执行时间为: " + (endTime - startTime) + " 毫秒"
            );

            logger.info("[AOP Around] end");
        }

        // 直接结果原封不动直接返回
        return result;
    }

}
