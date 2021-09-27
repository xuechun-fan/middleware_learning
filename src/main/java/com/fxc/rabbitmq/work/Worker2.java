package com.fxc.rabbitmq.work;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作线程1
 *
 * @author FXC
 */
public class Worker2 {
    /** 队列名称 */
    public static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("【worker2 等待接收消息...】");
        channel.basicConsume(QUEUE_NAME, false, (tag, deliver) -> {
            System.out.println("【worker2 接收到消息】： " + new String(deliver
                    .getBody(), StandardCharsets.UTF_8));
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
        }, (a) -> {});
    }

}
