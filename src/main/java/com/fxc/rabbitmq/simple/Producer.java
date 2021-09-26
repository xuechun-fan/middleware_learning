package com.fxc.rabbitmq.simple;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author FXC
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(RabbitmqUtils.QUEUE_NAME, false, false, false, null);
        String msg = "你好, rabbitmq";
        channel.basicPublish("", RabbitmqUtils.QUEUE_NAME, null, msg
                .getBytes(StandardCharsets.UTF_8));
        System.out.println("生产者发送了消息：" + msg);

    }
}
