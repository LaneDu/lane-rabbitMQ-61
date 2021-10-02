package com.galaxy.mqdemospringbootconsumer.config;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lane
 * @date 2021年08月26日 下午3:26
 */
@Configuration
public class ConsumerConfig {


    @Bean
    public Queue queueTTLWaiting() {

        Map<String, Object> props = new HashMap<>();

        // 对于该队列中的消息，设置都等待 10s
        props.put("x-message-ttl", 10000);

        Queue queue = new Queue("q.pay.ttl-waiting", true, false,

                false, props);


        return queue;

    }
    @Bean
    public Queue queueWaiting() {
        Queue queue = new Queue("q.pay.waiting", true, false, false);

        return queue;

    }

    @Bean
    public Queue myQueue() {
        return new Queue("myqueue123",false,false,false,null);
    }

}
