package com.fxc.rabbitmq.work;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，生产多个消息
 *
 * @author FXC
 */
public class NewTask {

    /** 队列名称 */
    public static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String msg = sc.next();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("【create new task】: " + msg);
        }
    }
}
