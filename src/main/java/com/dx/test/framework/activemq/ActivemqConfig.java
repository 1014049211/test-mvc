package com.dx.test.framework.activemq;

import com.dx.test.framework.base.util.DateUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.Session;

/**
 * ActiveMQ 配置类
 */
@Configuration
@PropertySource("classpath:properties/activemq.properties")
@EnableJms // Tips 开启 Spring 对于 JMS 的支持
public class ActivemqConfig {

    // 用户名
    private String username;

    // 密码
    private String password;

    // 地址
    private String brokerUrl;

    /**
     * 消息队列连接工厂, 缓存池模式
     *
     * @return CachingConnectionFactory
     */
    @Bean
    CachingConnectionFactory cachingConnectionFactory() {

        // Tips 相比于 PooledConnectionFactory, CachingConnectionFactory 增加了缓存机制
        //  对 sessions, connections 和 producers 进行缓存复用, 减少了开销, 效率更高

        return new CachingConnectionFactory(
                new ActiveMQConnectionFactory(this.username, this.password, this.brokerUrl)
        );
    }

    /**
     * Spring 提供的 JMS 操作模板, 提供自动的池操作
     * <p>
     * Tips 几乎所有的 Spring 提供的 XXXTemplate 都支持池操作, 例如 RedisTemplate
     *
     * @param cachingConnectionFactory CachingConnectionFactory
     * @return JmsTemplate
     */
    @Bean(name = "queueJmsTemplate")
    JmsTemplate queueJmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);

        // 不使用事务 TODO 消息队列事务管理
        jmsTemplate.setSessionTransacted(false);
        // 签收模式: auto
        jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        // 是否开启自定义的 deliveryMode(是否持久化)、priority(优先级)、timeToLive(消息存活时间)
        // Tips 这个属性不设置为 true, 对上诉三个属性的设置将无效, 依然会使用默认配置
        jmsTemplate.setExplicitQosEnabled(true);

        // Tips 设置消息持久化时, 发送的消息会根据 activemq.xml 中的配置存在指定的位置
        //  在消费者没有成功应答之前, 消息不会消失(可以设置存活时间), 同时可以保证只会被消费一次

        // 消息持久化时保存方式: PERSISTENT - 磁盘; NON_PERSISTENT - 内存
        // TODO 还可以持久化到数据库
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
//        jmsTemplate.setDeliveryPersistent(true); 这个跟 setDeliveryMode 功能是一样的, true 代表 DeliveryMode.PERSISTENT
        // 持久化的消息存活多久: 2 小时
        // Tips 不清楚这个单位是不是秒, 类型是 int, 应该是秒
        jmsTemplate.setTimeToLive(DateUtil.ONE_HOUR_OF_SECOND + DateUtil.ONE_HOUR_OF_SECOND);

        return jmsTemplate;
    }

    /**
     * Spring 提供的 JMS 操作模板, 提供自动的池操作
     * <p>
     * Tips 几乎所有的 Spring 提供的 XXXTemplate 都支持池操作, 例如 RedisTemplate
     *
     * @param cachingConnectionFactory CachingConnectionFactory
     * @return JmsTemplate
     */
    @Bean(name = "topicJmsTemplate")
    JmsTemplate topicJmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        // 不使用事务
        jmsTemplate.setSessionTransacted(false);
        // 签收模式: auto
        jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        // 主题模式, 默认是 false, 代表消息模式
        jmsTemplate.setPubSubDomain(true);
        // 是否开启自定义的 deliveryMode(是否持久化)、priority(优先级)、timeToLive(消息存活时间)
        jmsTemplate.setExplicitQosEnabled(true);
        // 消息持久化时保存方式: PERSISTENT - 磁盘; NON_PERSISTENT - 内存
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 持久化的消息存活多久: 2 小时
        jmsTemplate.setTimeToLive(DateUtil.ONE_HOUR_OF_SECOND + DateUtil.ONE_HOUR_OF_SECOND);

        return jmsTemplate;
    }

    /**
     * 队列消息监听
     *
     * @param cachingConnectionFactory CachingConnectionFactory
     * @return JmsListenerContainerFactory
     */
    @SuppressWarnings("rawtypes")
    @Bean(name = "queueListener")
    JmsListenerContainerFactory queueListener(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    /**
     * 主题消息监听
     *
     * @param cachingConnectionFactory CachingConnectionFactory
     * @return JmsListenerContainerFactory
     */
    @SuppressWarnings("rawtypes")
    @Bean(name = "topicListener")
    JmsListenerContainerFactory topicListener(CachingConnectionFactory cachingConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Value("${activemq.username}")
    public void setUsername(String username) {
        if (this.username == null) {
            this.username = username;
        }
    }

    @Value("${activemq.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    @Value("${activemq.brokerUrl}")
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }
}
