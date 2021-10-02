package com.galaxy.rabbitmq;

import com.rabbitmq.client.*;
import com.sun.tools.javac.Main;

import java.net.URISyntaxException;
import java.security.KeyManagementException;

/**
 * @author lane
 * @date 2021年09月01日 下午4:03
 */
public class PersistentProducer {

    public static void main(String[] args) throws Exception, KeyManagementException, URISyntaxException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:1234@mha:5672/%2f");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //将当前通道设置为发送方确认通道、
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        //参数分别为queue、持久化、排外、自删除、参数
        channel.queueDeclare("queue.lane2", true, false, false, null);
        //参数分别为exchange、类型、持久化、自删除、参数
        channel.exchangeDeclare("ex.lane2", BuiltinExchangeType.DIRECT, true, false, null);
        channel.queueBind("queue.lane2", "ex.lane2", "key.lane2");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2代表持久化消息
                .build();

        channel.basicPublish("ex.lane2","key.lane2",properties,"hello".getBytes());
        channel.close();
        connection.close();
    }
}
