package com.fxc.rabbitmq.topic;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 发送
 *
 * @author FXC
 */
public class Send1 {

    /** 交换机名字 */
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明绑定的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String msg1 = "hello world 1";
        String msg2 = "hello world 2";
        String msg3 = "hello world 3";
        String key1 = "quick.orange.rabbit";
        String key2 = "lazy.pink.rabbit";
        String key3 = "quick.orange.male.rabbit";
        channel.basicPublish(EXCHANGE_NAME, key1, null, msg1.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sender send message: " + msg1);
        channel.basicPublish(EXCHANGE_NAME, key2, null, msg2.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sender send message: " + msg2);
        channel.basicPublish(EXCHANGE_NAME, key3, null, msg3.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sender send message: " + msg3);

    }
}