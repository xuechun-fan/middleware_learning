package com.fxc.rabbitmq.confirm.async;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 同步确认模式
 *
 * @author FXC
 */
public class Producer {

    /** 队列名称 */
    public static final String QUEUE_NAME = "async";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = null;
        try {

            final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(
                    new TreeSet<Long>());

            channel = RabbitmqUtils.getChannel();
            // 开启确认模式
            channel.confirmSelect();

            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 添加channel监听器
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple) {
                        System.out.println("handleAck--success-->multiple" + deliveryTag);
                        confirmSet.headSet(deliveryTag + 1L).clear();
                    } else {
                        System.out.println("handleAck--success-->single" + deliveryTag);
                        confirmSet.remove(deliveryTag);
                    }
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    if (multiple) {
                        System.out.println("handleNAck--success-->multiple" + deliveryTag);
                        confirmSet.headSet(deliveryTag + 1L).clear();
                    } else {
                        System.out.println("handleNAck--success-->single" + deliveryTag);
                        confirmSet.remove(deliveryTag);
                    }
                }
            });

            while (true) {
                String msg = "hello hhh";
                // 获取未confirm的序列号
                Long seqNo = channel.getNextPublishSeqNo();
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
                // 将消息序号添加到未确认序号的集合中
                confirmSet.add(seqNo);
//                System.out.println("生产者发送了消息：" + msg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
