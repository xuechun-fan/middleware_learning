package com.fxc.rabbitmq.route;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式 发送
 *
 * @author FXC
 */
public class Send2 {

    /** 交换机名字 */
    private static final String EXCHANGE_NAME = "exchange_route";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明绑定的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String msg = sc.next();
            channel.basicPublish(EXCHANGE_NAME, "orange", null,
                                 msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sender send message: " + msg);
        }

    }
}