package com.dx.test.business.redis;

import com.dx.test.framework.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Redis 演示
 * Tips 请确保存在可用的 Redis 服务
 */
@Controller
@RequestMapping("testRedis")
public class TestRedisController {

    /**
     * 页面初始化
     */
    @RequestMapping("init")
    public String init() throws Exception {
        if (!RedisUtil.hasContent()) {
            throw new Exception("没有可用的 Redis 服务！");
        }
        return "test/redis";
    }

    /**
     * 向 Redis 中存储一个字符串
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(@RequestParam String key, String value) {
        if (RedisUtil.put(key, value)) {
            return "保存成功!";
        } else {
            return "保存失败!";
        }
    }

    /**
     * 向 Redis 中存储一个字符串
     */
    @RequestMapping("query")
    @ResponseBody
    public String query(@RequestParam String key) {
        /*
         * Tips 如果之前存储的就是一个空值, 那么这次获取到空值就会产生一次缓存穿透
         * Tips 缓存穿透
         *  现象: 获取一个值为空的缓存, 因为没查到值而访问数据库, 导致每一次查询都会访问数据库
         *  解决: 缓存时发现值本身就是空时, 将空设置为一个特定的值, 例如 -1
         */
        return RedisUtil.getString(key);
    }

}
