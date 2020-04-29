package com.dx.test.framework.redis;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * 是否加载 Redis 的判断依据
 * Tips 实现 Condition 可以让类成为一个条件, 配合 @Conditional 注解可以控制 Spring 的初始化过程, 例如是否加载某个 Bean
 * Tips 当前条件为 "是否能获取到 Redis 连接"
 */
public class RedisCondition implements Condition {

    /**
     * Tips 只有这一个方法, 返回值是 boolean, true 代表条件成立, false 代表条件不成立
     *
     * @param context  ConditionContext 可以获得容器及环境信息
     * @param metadata AnnotatedTypeMetadata 可以获得当前进行条件判断的对象的信息
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            // 获取环境
            Environment environment = context.getEnvironment();
            // 通过环境读取配置文件中的 redis 地址和端口号, 并验证 redis 是否可以连接
            return RedisUtil.hasContent(environment.getProperty("redis.host"),
                    Integer.parseInt(Objects.requireNonNull(environment.getProperty("redis.port"))));
        } catch (Exception e) {
            return false;
        }
    }
}
