package com.dx.test.business.activemq;

import com.dx.test.framework.activemq.ActivemqCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费者类
 */
@Component
@DependsOn("activemqConfig")
@Conditional(ActivemqCondition.class)
public class TestConsumer {

    // --------------- 队列消息的消费者 ------------------
    @JmsListener(destination = "test-queue", containerFactory = "queueListener")
    public void queueConsumer1(String message) {
        System.out.println("queueConsumer1 接到了这个消息: " + message);
    }

    @JmsListener(destination = "test-queue", containerFactory = "queueListener")
    public void queueConsumer2(String message) {
        System.out.println("queueConsumer2 接到了这个消息: " + message);
    }

    @JmsListener(destination = "test-queue", containerFactory = "queueListener")
    public void queueConsumer3(String message) {
        System.out.println("queueConsumer3 接到了这个消息: " + message);
    }


    // --------------- 主题消息的消费者 ------------------
    @JmsListener(destination = "test-topic", containerFactory = "topicListener")
    public void topicConsumer1(String message) {
        System.out.println("topicConsumer1 接到了这个消息: " + message);
    }

    @JmsListener(destination = "test-topic", containerFactory = "topicListener")
    public void topicConsumer2(String message) {
        System.out.println("topicConsumer2 接到了这个消息: " + message);
    }

    @JmsListener(destination = "test-topic", containerFactory = "topicListener")
    public void topicConsumer3(String message) {
        System.out.println("topicConsumer3 接到了这个消息: " + message);
    }

}
