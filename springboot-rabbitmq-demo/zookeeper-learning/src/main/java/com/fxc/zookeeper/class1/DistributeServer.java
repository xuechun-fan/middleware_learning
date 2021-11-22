package com.fxc.zookeeper.class1;

import com.fxc.zookeeper.utils.ZkUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.Random;

/**
 * 服务注册发现 服务端
 *
 * @author FXC
 */
public class DistributeServer {

    private static final Random RANDOM = new Random();
    private static ZooKeeper conn;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        DistributeServer server = new DistributeServer();
        // 1、获取zk连接
        conn = ZkUtils.getConnect(watchedEvent -> {});

        // 2、注册服务器到zk集群
        server.registry();

        // 3、启动业务逻辑
        ZkUtils.business(600);
    }


    /**
     * 注册服务
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void registry() throws KeeperException, InterruptedException {
        for (int i = 1; i <= 10; i++) {
            String hostName = "server-" + i + " " + RANDOM.nextInt(100) + " nodes";
            conn.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(hostName + " is online...");
        }

    }


}