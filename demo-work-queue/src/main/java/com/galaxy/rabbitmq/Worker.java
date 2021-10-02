package com.galaxy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年08月18日 下午7:28
 */
public class Worker {
    private static final String TASK_QUEUE_NAME = "lane.task.queue";

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
        channel.queueDeclare("queue.biz", false, false, true, null);
        // true表示不需要手动确认消息，false表示需要手动确认消息： channel.basicAck(xxx, yyy);
        boolean autoAck = true;
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, (consumerTag, delivery) -> {
                    String task = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + task + "'");
                    try {
                        doWork(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                consumerTag -> {
                });
    }

    private static void doWork(String task) throws InterruptedException {
        System.out.println("task = " + task);
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                Thread.sleep(100);
        }
    }
}
