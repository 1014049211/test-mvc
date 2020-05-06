package com.dx.test.framework.redis;

import com.dx.test.framework.base.util.DateUtil;
import com.dx.test.framework.base.util.StringUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 配置类
 * Tips 当前类中配置的 Bean 都根据条件类 RedisCondition 来判断是否注册到 Spring
 */
@Configuration
@PropertySource("classpath:properties/redis.properties") // Tips @PropertySource 可以加载 properties 文件里的内容
public class RedisConfig {

    // 默认的服务地址
    protected static final String DEFAULT_HOST = "127.0.0.1";
    // 默认端口号
    protected static final int DEFAULT_PORT = 6379;

    // 地址
    private String host;

    // 端口号
    private Integer port;

    /**
     * Jedis 连接池配置
     *
     * @return JedisPoolConfig
     */
    @Bean
    @Conditional(RedisCondition.class)
    JedisPoolConfig jedisPoolConfig() {
        // Tips JedisPoolConfig 继承自通用的池连接配置类 GenericObjectPoolConfig, 有很多配置项, 此处显式列举一些常用的配置
        // Tips 池的配置跟是否生产环境关系不大, 就不单列出来作为 properties 了, 直接写在 Java 里
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数, 默认 8
        jedisPoolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL);
        // 最大空闲连接数, 默认 8
        jedisPoolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE);
        // 最小空闲连接数, 默认 0
        jedisPoolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE);
        // 当连接池资源耗尽以后, 调用者是否等待, 默认 true, 代表等待
        jedisPoolConfig.setBlockWhenExhausted(GenericObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED);
        // 当连接池资源耗尽以后, 调用者等待资源的最大时间, 单位毫秒, 默认为 -1, 代表一直等待, 此处设置为 20 秒
        // Tips 当 BlockWhenExhausted 为 false 时, 此设置无效
        jedisPoolConfig.setMaxWaitMillis(DateUtil.ONE_SECOND_OF_MILLI * 20);
        // 是否开启 jxm 端口, 默认 true, 代表开启
        // Tips jxm 端口可以监控 Java 程序的基本信息和运行情况
        //  jxm 开启后可以使用 JConsole 或 Visual VM 查看连接池的相关统计
        // TODO jxm JConsole Visual VM
        jedisPoolConfig.setJmxEnabled(GenericObjectPoolConfig.DEFAULT_JMX_ENABLE);
        // 连接空闲多长时间会被逐出, 默认 30 分钟
        jedisPoolConfig.setMinEvictableIdleTimeMillis(GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        // 检测连接是否空闲时的采样数, 默认 3
        jedisPoolConfig.setNumTestsPerEvictionRun(GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
        // 提取连接时是否检测连接有效性, 默认 false, 代表不检测
        jedisPoolConfig.setTestOnBorrow(GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW);
        // 归还连接时是否检测连接有效性, 默认 false, 代表不检测
        jedisPoolConfig.setTestOnReturn(GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN);
        // 提取连接时是否做空间检测, 空间连接会被清除, 默认 false, 不检测
        jedisPoolConfig.setTestWhileIdle(GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE);

        return jedisPoolConfig;
    }

    /**
     * Jedis 连接池
     */
    @Bean
    @Conditional(RedisCondition.class)
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        /// 创建连接池: 池配置 + redis 服务器所在 ip + redis 服务端口号
        return new JedisPool(jedisPoolConfig, host, port);
    }

    /**
     * Jedis 连接工厂
     */
    @Bean
    @Conditional(RedisCondition.class)
    JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(jedisPoolConfig);
    }

    /**
     * RedisTemplate
     */
    @SuppressWarnings("rawtypes")
    @Bean
    @Conditional(RedisCondition.class)
    RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    /**
     * StringRedisTemplate
     */
    @Bean
    @Conditional(RedisCondition.class)
    StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

    // Tips @Value 可以将读取到的配置信息赋值给属性或作为参数传递给 set 方法
    @Value("${redis.host}")
    public void setHost(String host) {
        if (this.host != null) {
            return;
        }
        this.host = StringUtil.isEmpty(host) ? DEFAULT_HOST : host;
    }

    @Value("${redis.port}")
    public void setPort(Integer port) {
        if (this.port != null) {
            return;
        }
        this.port = port == null ? DEFAULT_PORT : port;
    }
}
