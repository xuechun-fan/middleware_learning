package com.fxc.producer.middleware.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 生产者
 *
 * @author FXC
 */
@Component
public class Sender {

    /** rabbitmq模板 */
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     */
    public void send(Object object) {
        rabbitTemplate.convertAndSend("topic_exchange", "a.orange.b", object);
    }

}