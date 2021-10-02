package com.galaxy.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author lane
 * @date 2021年08月21日 下午4:32
 */
public class SpringAnnotationDemo {
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
       //发送消息
        template.convertAndSend("myqueue", "hello world");
        String msg = (String) template.receiveAndConvert("myqueue");
        System.out.println(msg);

        context.close();

}

}