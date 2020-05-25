package com.dx.test.framework.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息队列工具类
 */
@Component
@Conditional(ActivemqCondition.class)
public class ActivemqUtil {

    /**
     * 用于发送队列消息
     */
    private static JmsTemplate queueJmsTemplate;

    /**
     * 用于发送主题消息
     */
    private static JmsTemplate topicJmsTemplate;

    /**
     * 发送一个队列消息
     *
     * @param destination 地址
     * @param message     消息内容
     */
    public static void sendQueue(String destination, Object message) {

        /*
         * Tips ※ 关于发送的消息类型
         *  1. 如果是不同的项目间使用实体类对象传递消息, 那么类结构和类的包路径, 都要完全一致, 否则在反序列化的过程中会报错
         *  2. 发送的消息要尽量简洁, 最好只是一个 id, 如果要一次传送多个数据, 最好使用 Map, 可以避免第一条的情况
         */

        queueJmsTemplate.convertAndSend(new ActiveMQQueue(destination), message);
    }

    /**
     * 发送一个主题消息
     *
     * @param destination 地址
     * @param message     消息内容
     */
    public static void sendTopic(String destination, Object message) {
        topicJmsTemplate.convertAndSend(new ActiveMQTopic(destination), message);
    }

    @Autowired(required = false)
    public void setJmsTemplate(JmsTemplate queueJmsTemplate) {
        if (ActivemqUtil.queueJmsTemplate == null) {
            ActivemqUtil.queueJmsTemplate = queueJmsTemplate;
        }
    }

    @Autowired(required = false)
    public void setTopicJmsTemplate(JmsTemplate topicJmsTemplate) {
        if (ActivemqUtil.topicJmsTemplate == null) {
            ActivemqUtil.topicJmsTemplate = topicJmsTemplate;
        }
    }
}
