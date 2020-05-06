package com.dx.test.framework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * Redis 工具类
 */
@Component
// Tips @DependsOn 可以设置当前 Bean 在 name 为 "redisConfig" 的 Bean 加载以后再加载
//  因为条件判断需要使用到 RedisConfig 中加载的配置文件 redis.properties
//  如果当前 Bean 在 RedisConfig 前加载(Spring 的默认加载是无序的), 条件判断读取不到配置文件一定会返回 false
//  导致当前 Bean 中使用 @Autowired 标记的依赖无法注入
@DependsOn("redisConfig")
//@Conditional(RedisCondition.class)
// Tips 条件放在这是无效的, 因为是否注册的优先级比怎么注册高
//  Spring 会先根据 @Conditional 去判断是否注册, 再根据 @DependsOn 解析怎么注册
//  此时 RedisCondition 中读取不到 RedisConfig 里加载的配置文件, 条件一定不成立
public class RedisUtil {

    /**
     * Jedis 连接池
     */
    private static JedisPool jedisPool;

    /**
     * RedisTemplate
     */
    @SuppressWarnings("rawtypes")
    private static RedisTemplate redisTemplate;

    /**
     * StringRedisTemplate
     */
    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 监测 Redis 是不是挂了
     *
     * @return 能 ping 就返回 true, 其余返回 false;
     */
    public static boolean hasContent(String host, int port) {
        // Tips 每次都新建链接, 大量使用的时候性能差
        try {
            return "PONG".equalsIgnoreCase(new Jedis(host, port).ping());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 监测 Redis 是不是挂了
     *
     * @return 能 ping 就返回 true, 其余返回 false;
     */
    public static boolean hasContent() {
        try {
            return "PONG".equalsIgnoreCase(new Jedis(RedisConfig.getHost(), RedisConfig.getPort()).ping());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 向 Redis 中存一个字符串类型的值
     *
     * @param key   键
     * @param value 字符串值
     * @return 是否成功
     */
    public static boolean put(String key, String value) {
        try (Jedis jedis = RedisUtil.jedisPool.getResource()) {
            // 获取连接, 并存储
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 向 Redis 中存一个有过期时间的字符串类型的值
     *
     * @param key     键
     * @param value   字符串值
     * @param seconds 过期时间, 单位秒
     * @return 是否成功
     */
    public static boolean put(String key, String value, int seconds) {
        try (Jedis jedis = RedisUtil.jedisPool.getResource()) {
            // 获取连接, 并存储
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从 Redis 获取一个字符串值
     *
     * @param key 键
     * @return 字符串值
     */
    public static String getString(String key) {
        try (Jedis jedis = RedisUtil.jedisPool.getResource()) {
            // 获取连接, 并存储
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从 Redis 中获取一个字符串值, 并将这个值从 Redis 中删除
     *
     * @param key 键
     * @return 字符串值
     */
    public static String cutString(String key) {
        try (Jedis jedis = RedisUtil.jedisPool.getResource(); Pipeline pipeline = jedis.pipelined()) {
            // Tips Pipeline 操作类相当于数据库的事务操作, 此时获取到的 value 并没有值, 因为还未提交
            Response<String> value = pipeline.get(key);
            // Tips 因为由同一个 Pipeline 执行, 所以此处的 del 操作和上一步的 get 操作, 要么一起成功, 要么都失败
            pipeline.del(key);
            // Tips 这个操作就相当于提交事务, sync 执行以后, 才能从 value 对象中获得值
            pipeline.sync();
            return value.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired(required = false)
    public void setJedisPool(JedisPool jedisPool) {

        /*
         * Tips 静态属性的注入
         *  静态属性属于类, 不属于对象, Spring 对于 Bean 的依赖注入是基于对象的
         *  所以给静态属性添加 @Autowired 是无效的
         *  正确的做法是给静态属性定义一个非静态的 set 方法, 给这个 set 方法添加 @Autowired
         */

        // 只允许初始化一次
        if (RedisUtil.jedisPool == null) {
            RedisUtil.jedisPool = jedisPool;
        }
    }

    @SuppressWarnings("rawtypes")
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        if (RedisUtil.redisTemplate == null) {
            RedisUtil.redisTemplate = redisTemplate;
        }
    }

    @Autowired(required = false) // Tips @Autowired 的 required 默认是 true, 找不到可注入的 Bean 时会报错
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        if (RedisUtil.stringRedisTemplate == null) {
            RedisUtil.stringRedisTemplate = stringRedisTemplate;
        }
    }

    @SuppressWarnings("rawtypes")
    public static RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public static StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

}
