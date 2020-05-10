package com.dx.test.framework.base.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听器: 监听 Spring 容器的刷新事件, Spring 容器在初始化时会调用 refresh 方法, 所以也会触发此方法
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    /*
     * Tips 是每个要打印日志的类都要创建一个 Logger 还是大家都功用一个
     *  1. 理想状态下, 日志记录作为一个横切性工作, 不应该在业务代码中频繁出现, 即便是每个类都创建一个 Logger 也不会太多太繁琐
     *  2. 不同的 Logger 在日志中是有区分体验的, 如果都是用一个公共的, 就没有区分了
     *  3. 如果一个公共的 Logger 还要区分, 可以使用 Thread.currentThread().getStackTrace() 获得调用 Logger 的类
     *  4. 另一个不用每个类都写一个 Logger 的办法是继承, 父类定义 Logger, 子类使用, Spring 就是这么干的
     *
     * Tips Thread.currentThread().getStackTrace()
     *  获取当前线程的堆栈信息, 是一个数组, [1] 是当前方法, [2] 是调用当前方法的方法, 以此类推
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.trace("Spring 容器已重新初始化!!!");
        logger.debug("Spring 容器已重新初始化!!!");
        logger.info("Spring 容器已重新初始化!!!");
        logger.warn("Spring 容器已重新初始化!!!");
        logger.error("Spring 容器已重新初始化!!!");
    }
}
