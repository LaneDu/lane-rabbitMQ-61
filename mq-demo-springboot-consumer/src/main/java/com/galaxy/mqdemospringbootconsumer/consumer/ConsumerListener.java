package com.galaxy.mqdemospringbootconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author lane
 * @date 2021年08月26日 下午2:38
 */
@Component
public class ConsumerListener {

    @RabbitListener(queues = "myqueue123")
    public void service2(@Payload String message , @Header(name = "hello") String name) {
        System.out.println("消息队列推送来的消息：" + message);
        System.out.println("消息队列推送来header hello：" + name);
    }

//    @RabbitListener(queues = "myqueue123")
//    public void service1(@Payload String message) {
//        System.out.println("消息队列推送来的消息：" + message);
//
//    }
    @RabbitListener(queues = "q.pay.ttl-waiting")
    public void service123(@Payload String message) {
        System.out.println("消息队列推送来的消息123：" + message);

    }@RabbitListener(queues = "q.pay.waiting")
    public void service3(@Payload String message) {
        System.out.println("消息队列推送来的消息123：" + message);

    }
}
