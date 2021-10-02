package com.galaxy.mqdemospringbootproducer.controller;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author lane
 * @date 2021年08月21日 下午4:53
 */

@RestController
@RequestMapping("/rabbitmq")
public class ProducerController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/{message}")
    public String sendMsg(@PathVariable("message") String message) throws UnsupportedEncodingException {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("utf-8")
                .setHeader("hello", "world")
                .build();

        Message msg = MessageBuilder.withBody(message.getBytes("utf-8"))
                .andProperties(messageProperties)
                .build();
        rabbitTemplate.send("myex","mykey",msg);
        rabbitTemplate.convertAndSend("myex", "mykey", message);
        return "ok";
    }

    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable("message") String message) {

        rabbitTemplate.convertAndSend("myex", "mykey", "发送:"+message);

        return "send ok";

    }

}
