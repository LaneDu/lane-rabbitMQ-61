package com.galaxy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年08月19日 上午9:56
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
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
        //创建channel
        Channel channel = connection.createChannel();
        //声明fanout类型的交换器：每个订阅者都发送一份
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //消息发送
        String message = "今天是星期几啊？";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes("utf-8"));

        System.out.println(" [x] Sent '" + message + "'");

    }

}
