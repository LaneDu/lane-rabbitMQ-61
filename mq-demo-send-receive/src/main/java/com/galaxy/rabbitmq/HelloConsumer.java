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
public class HelloConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机名称 hostname

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
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //声明交换器exchange
        //交换器名称、类型、是否持久化、是否自动删除、属性集合
        channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT,false,false,null);
        //将交换器和消息队列绑定
        //消息队列名称、交换器名称、路由key
        channel.queueBind("queue.biz","ex.biz","hello.world");

        /* 使用服务器生成的consumerTag启动本地，非排他的使用者。
        启动一个 仅提供了basic.deliver和basic.cancel AMQP方法（对大多数情形够用了）
        第一个参数：队列名称 autoAck – true 只要服务器发送了消息就表示消息已经被消费者确认;
        false服务 端等待客户端显式地发送确认消息
        deliverCallback – 服务端推送过来的消息回调函数
        cancelCallback – 客户端忽略该消息的回调方法 Returns:
        服务端生成的consumerTag
        */
        channel.basicConsume("queue.biz", true,
                (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> { });

        // 消息的推送回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //关闭通道
//        channel.close();
        //关闭连接
//        connection.close();


    }



}
