package com.fxc.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq工具类
 *
 * @author FXC
 */
public class RabbitmqUtils {

    /** 队列名称 */
    public static final String QUEUE_NAME = "hello";



    /**
     * 获取rabbitmq链接channel
     *
     * @return 创建好的channel
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setVirtualHost("/myHost");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();
        return connection.createChannel();
    }

}
