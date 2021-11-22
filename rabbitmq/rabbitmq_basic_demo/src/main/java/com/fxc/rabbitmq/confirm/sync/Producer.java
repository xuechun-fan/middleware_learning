package com.fxc.rabbitmq.confirm.sync;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 同步确认模式
 *
 * @author FXC
 */
public class Producer {

    /** 队列名称 */
    public static final String QUEUE_NAME = "confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        try (Channel channel = RabbitmqUtils.getChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 开启同步确认模式
            channel.confirmSelect();

            String msg = "hello word...";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));

            System.out.println("生产者发送了消息：" + msg);

            // 普通确认
            if (channel.waitForConfirms()) {
                System.out.println("确认成功。。。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
