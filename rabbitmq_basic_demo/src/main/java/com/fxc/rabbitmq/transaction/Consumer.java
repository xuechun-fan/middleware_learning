package com.fxc.rabbitmq.transaction;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 事务机制控制
 *
 * @author FXC
 */
public class Consumer {

    /** 队列名称 */
    public static final String QUEUE_NAME = "transaction";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitmqUtils.getChannel();
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("消费者收到消息：" + new String(message
                                                                   .getBody(), StandardCharsets.UTF_8));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, (a) -> { });

    }
}
