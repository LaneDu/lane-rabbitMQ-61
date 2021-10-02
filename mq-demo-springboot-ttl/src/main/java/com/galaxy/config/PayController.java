package com.galaxy.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author lane
 * @date 2021年09月02日 下午4:10
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    AmqpTemplate rabbitTemplate;

    @RequestMapping("/queuettl")
    public String sendMessage() {

        rabbitTemplate.convertAndSend("ex.pay.ttl-waiting", "pay.ttl-waiting", "发送了TTL-WAITING-MESSAGE");

        return "queue-ttl-ok";

    }
    @RequestMapping("/msgttl")
    public String sendTTLMessage() throws UnsupportedEncodingException {

        MessageProperties properties = new MessageProperties();
        properties.setExpiration("5000");

        Message message = new Message("发送了WAITINGMESSAGE".getBytes("utf-8"), properties);

        rabbitTemplate.convertAndSend("ex.pay.waiting", "pay.waiting", message);
        return "msg-ttl-ok"; }

}
