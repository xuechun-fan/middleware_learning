package com.fxc.zookeeper;

import com.fxc.zookeeper.utils.ZkUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Zookeeper客户端
 *
 * @author FXC
 */
public class ClientDemoTest {


    private ZooKeeper zkClient;


    @Before
    public void init() throws IOException {
        // 初始化客户端
        zkClient = ZkUtils.getConnect(watchedEvent -> {
            // 服务端数据变化 执行该回调函数
            try {
                List<String> children = zkClient.getChildren("/", true);
                System.out.println("===========================================");
                for (String child : children) {
                    System.out.println(child);
                }
                System.out.println("===========================================");
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Test
    public void create() throws KeeperException, InterruptedException {
        String nodeCreated = zkClient.create("/dmu",
                                             "Welcome to Dalian Maritime University".getBytes(),
                                             ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        zkClient.getChildren("/", true);
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat exists = zkClient.exists("/dmu", false);
        System.out.println(exists == null ? "不存在" : "存在");
    }


}