package com.dx.test.business.activemq;

import com.dx.test.framework.activemq.ActivemqUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示 ActiveMQ
 */
@Controller
@RequestMapping("testActivemq")
public class TestActivemqController {

    @RequestMapping("init")
    public String init() {
        return "test/activemq";
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     * @param type    消息类型
     * @return 发送结果
     */
    @RequestMapping("send")
    @ResponseBody
    public String send(String message, String type) {
        if ("queue".equals(type)) {
            ActivemqUtil.sendQueue("test-queue", message);
        }
        if ("topic".equals(type)) {
            ActivemqUtil.sendTopic("test-topic", message);
        }
        return "发送成功, 请查看服务端控制台!";
    }

}
