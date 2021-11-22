package com.fxc.zookeeper.class1;

import com.fxc.zookeeper.utils.ZkUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务注册发现 客户端
 *
 * @author FXC
 */
public class DistributeClient {

    private ZooKeeper conn;

    public static void main(String[] args) throws KeeperException, InterruptedException {

        DistributeClient client = new DistributeClient();
        // 1、获取连接
        client.conn = ZkUtils.getConnect(watchedEvent -> {
            try {
                client.getServerList();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 2、监听 /servers 下面子节点的增加和删除
        client.getServerList();

        // 3、业务逻辑
        ZkUtils.business(600);

    }

    private void getServerList() throws KeeperException, InterruptedException {
        List<String> children = conn.getChildren("/servers", true);
        List<String> servers = new ArrayList<>();
        for (String child : children) {
            byte[] data = conn.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }
        System.out.println("-----------------" + new Date().toString() + "--------------------");
        if (servers.size() == 0) {
            System.out.println("当前没有服务器在线... ...");
        } else {
            servers.forEach(System.out::println);
        }
        System.out.println("------------------------------------------------------------------");
    }
}