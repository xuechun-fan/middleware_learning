package com.fxc.rabbitmq.transaction;

import com.fxc.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 事务机制控制
 *
 * @author FXC
 */
public class Producer {

    /** 队列名称 */
    public static final String QUEUE_NAME = "transaction";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = null;

        try {
            channel = RabbitmqUtils.getChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 开启事务
            channel.txSelect();

            String msg = "hello word...";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));

            // 故意导致异常，令mq事务回滚
//            int i = 1 / 0;

            // 提交事务
            channel.txCommit();
            System.out.println("生产者发送了消息：" + msg);

        } catch (Exception e) {
            assert channel != null;
            System.err.println("发送消息出现异常，rabbitmq事务回滚");
            channel.txRollback();
        } finally {
            if (channel != null) {
                channel.close();
            }
        }

    }
}
