package com.galaxy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年08月18日 下午7:23
 */
public class NewTask {

    private static final String QUEUE_NAME = "";
    private static final String[] works = {

            "hello.", "hello..", "hello...", "hello....",
            "hello.....", "hello......", "hello.......",
            "hello........", "hello.........", "hello.........."};

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机名称 hostname 需要在host文件中添加 ip hostname 如 172.16.94.13 mha
        connectionFactory.setHost("mha");
        //设置虚拟主机名称默认 / (在url中的转义字符 %2f)
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("1234");
        //新建tcp连接
        Connection connection = connectionFactory.newConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明消息队列
        //消息队列名称、是否持久化、是否排他、是否自动删除、属性信息
        channel.queueDeclare("lane.task.queue", false, false, false, null);
        for (String work : works) {
            // 将消息路由到taskQueue队列
            channel.basicPublish("", "lane.task.queue", null, work.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + work + "'");
        }
    }

}
