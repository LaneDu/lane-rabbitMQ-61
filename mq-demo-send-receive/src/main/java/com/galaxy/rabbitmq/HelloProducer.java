package com.galaxy.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author lane
 * @date 2021年08月17日 下午6:46
 * Rabbitmq 是一个消息 broker：接收消息，传递给下游应用
 *
 * 术语：
 * Producing 就是指发送消息，发送消息的程序是 Producer
 * Queue 指的是 RabbitMQ 内部的一个组件，消息存储于 queue 中。queue 使用主机的内存和磁盘存

 储，收到内存和磁盘空间的限制

 * 可以想象为一个大的消息缓冲。很多 Producer 可以向同一个 queue 发送消息，很多消费者

 可以从同一个 queue 消费消息。

 * Consuming 就是接收消息。一个等待消费消息的应用程序称为 Consumer
 *
 * 生产者、消费者、队列不必在同一台主机，一般都是在不同的主机上的应用。一个应用可以同时是

 生产者和消费者。

 *

 */
public class HelloProducer {
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
        channel.queueDeclare("queue.biz",false,false,true,null);

        String message = "hello world" ;
        //发送信息
        channel.basicPublish("", "queue.biz", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //关闭通道
        channel.close();
        //关闭连接
        connection.close();


    }



}
