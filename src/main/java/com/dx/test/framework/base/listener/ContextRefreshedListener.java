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
