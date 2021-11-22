package com.fxc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * CuratorDemo
 * Curator 是 Netflix 公司开源的一套 zookeeper 客户端框架，解决了很多 Zookeeper 客户端非常底层的细节开发工作，
 * 包括连接重连、反复注册 Watcher 和 NodeExistsException 异常等。
 *
 * Curator 包含了几个包：
 * ~ curator-framework：对 zookeeper 的底层 api 的一些封装。
 * ~ curator-client：提供一些客户端的操作，例如重试策略等。
 * ~ curator-recipes：封装了一些高级特性，如：Cache 事件监听、选举、分布式锁、分布式计数器、分布式 Barrier 等。
 *
 * @author FXC
 */
public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.243.134:2181,192.168.243.135:2181,192.168.243.136:2181")
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("")
                .build();

        curatorFramework.start();
        Stat stat = new Stat();
        // 查询节点数据
        byte[] data = curatorFramework.getData().storingStatIn(stat).forPath("/fxc");
        System.out.println("获取到数据为:\n" + new String(data));
        curatorFramework.close();
    }
}