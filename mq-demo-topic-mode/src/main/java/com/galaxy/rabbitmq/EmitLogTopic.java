package com.galaxy.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年08月19日 上午11:32
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "lane.topic.ex";
    private static final String[] SPEED = { "slow","normal","fast" };
    private static final String[] COLOR = { "white","black","colorful" };
    private static final String[] Animal = { "dog","cat","rabbit" };

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //消息发送
        String routingKey = "";
        for (int i = 0; i <30 ; i++) {
            Random random = new Random();
            int ran1 = random.nextInt(100);
            int num1 = ran1%3;
            String speed = SPEED[num1];
            ran1 = random.nextInt(100);
            num1 = ran1%3;
            String color = COLOR[num1];
            ran1 = random.nextInt(100);
            num1 = ran1%3;
            String animal = Animal[num1];
            routingKey=speed+"."+color+"."+animal;
            String message =  "这是 【" + routingKey + "】 的消息,序号是："+i;
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes("utf-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
