package org.fxc.consumer.middleware.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author FXC
 */

// 指定监听的队列
@RabbitListener(queues = "topic_queue")
@Component
public class Consumer {

    /**
     * 处理监听到的消息
     *
     * @param message
     */
    @RabbitHandler
    public void handleMessage(String message) {
        System.out.println("【consumer】 接收到消息： " + message);
    }


}