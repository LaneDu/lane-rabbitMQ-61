package com.galaxy.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年08月19日 上午10:56
 */
public class ReceiveRabbitLogsDirect {

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
        //声明direct类型的交换器：根据路由发送
        channel.exchangeDeclare("lane.topic.ex", BuiltinExchangeType.TOPIC);
        //创建临时队列
        String queueName = channel.queueDeclare().getQueue();
        //绑定rabbit路由的消息
        channel.queueBind(queueName,"lane.topic.ex","#.rabbit");
        //接收消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        //接收消息
        channel.basicConsume(queueName,true, deliverCallback, consumerTag -> {});

    }


}
