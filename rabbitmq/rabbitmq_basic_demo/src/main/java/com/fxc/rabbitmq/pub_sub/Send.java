package com.fxc.rabbitmq.pub_sub;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 发送
 *
 * @author FXC
 */
public class Send {

    /** 交换机名字 */
    private static final String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明绑定的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String msg = sc.next();
            channel.basicPublish(EXCHANGE_NAME, "", null,
                                 msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sender send message: " + msg);
        }

    }
}