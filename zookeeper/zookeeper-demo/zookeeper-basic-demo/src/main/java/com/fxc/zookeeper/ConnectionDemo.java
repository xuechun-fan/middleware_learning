package com.fxc.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper连接样例
 *
 * @author FXC
 */
public class ConnectionDemo {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(
                    "192.168.243.134:2181,192.168.243.135:2181,192.168.243.136:2181", 4000,
                    event -> {
                        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                            //如果收到了服务端的响应事件，连接成功
                            latch.countDown();
                        }
                    });
            latch.await();
            //CONNECTED
            System.out.println(zooKeeper.getState());
            String nodeCreated = zooKeeper.create("/dmu",
                                                  "Welcome to Dalian Maritime University".getBytes(),
                                                  ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(nodeCreated);
        } catch (InterruptedException | IOException | KeeperException e) {
            e.printStackTrace();
        }
    }
}