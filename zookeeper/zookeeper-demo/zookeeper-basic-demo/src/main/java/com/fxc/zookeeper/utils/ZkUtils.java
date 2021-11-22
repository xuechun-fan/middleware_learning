package com.fxc.zookeeper.utils;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Zookeeper工具类
 *
 * @author FXC
 */
public class ZkUtils {

    /** Zookeeper服务集群地址 */
    private static final String CONNECT_STRING =
            "192.168.243.134:2181,192.168.243.135:2181,192" + ".168.243.136:2181";
    /** 连接超时时间 */
    private static final int TIMEOUT = 60000;

    /**
     * 创建Zookeeper客户端
     *
     * @param watcher 传入监听事件
     * @return Zookeeper客户端
     */
    public static ZooKeeper getConnect(Watcher watcher) {
        ZooKeeper client = null;
        try {
            client = new ZooKeeper(CONNECT_STRING, TIMEOUT, watcher);
        } catch (IOException e) {
            System.err.println("创建ZK客户端失败");
        }
        if (Objects.isNull(client)) {
            System.err.println("创建ZK客户端失败");
        }
        return client;
    }

    /**
     * 模拟业务逻辑执行
     *
     * @param time 睡眠时长,单位为秒
     * @throws InterruptedException
     */
    public static void business(int time) throws InterruptedException {
        TimeUnit.SECONDS.sleep(time);
        System.out.println("业务执行完毕......");
    }
}