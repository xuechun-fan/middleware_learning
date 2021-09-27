package com.fxc.rabbitmq.topic;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 订阅者1
 *
 * @author FXC
 */
public class Sub1 {

    /** 交换机名字 */
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列，排他队列
        String queue = channel.queueDeclare().getQueue();
        // 队列和交换机绑定
        channel.queueBind(queue, EXCHANGE_NAME, "*.orange.*");
        System.out.println("【sub1】 waiting for messages...");

        DeliverCallback deliverCallback = (s, delivery) -> System.out.println(
                "【sub1】 接收到消息： " + new String(delivery.getBody(), StandardCharsets.UTF_8));

        channel.basicConsume(queue, true, deliverCallback, (a) -> {});
    }
}