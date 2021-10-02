package com.galaxy.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @author lane
 * @date 2021年09月01日 上午11:53
 */
public class Producer {

    public static void main(String[] args) throws Exception, KeyManagementException, URISyntaxException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:1234@mha:5672/%2f");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //将当前通道设置为发送方确认通道
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        channel.queueDeclare("queue.lane1",true,false,false,null);
        channel.exchangeDeclare("ex.lane1", BuiltinExchangeType.DIRECT,true,false,null);
        channel.queueBind("queue.lane1","ex.lane1","key.lane1");
        //发送消息
        channel.basicPublish("ex.lane1","key.lane1",null,"hello".getBytes("utf-8" ));
        //同步等待mq消息ack
        try {
            channel.waitForConfirmsOrDie(5_000);
            System.out.println("消息已经确认");

        }catch (IOException ioException){
            System.out.println("消息拒绝");
        }
        catch (IllegalStateException illegalStateException){
            System.out.println("消息的通道不是confirm");
        }
        catch (TimeoutException timeoutException){
            System.out.println("等待超时");
        }
        channel.close();
        connection.close();

    }




}
