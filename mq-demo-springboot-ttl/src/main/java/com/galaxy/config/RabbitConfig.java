package com.galaxy.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lane
 * @date 2021年09月02日 下午3:29
 */
@Configuration
public class RabbitConfig {

    private Queue queueTTL;
    private Exchange exchangeTTL;
    private Queue queueWait;
    private Exchange exchangeWait;

    @Bean
    public Queue queueTTLWaiting() {

        Map<String, Object> props = new HashMap<>();

        // 对于该队列中的消息，设置都等待 10s
        props.put("x-message-ttl", 10000);

        Queue queue = new Queue("q.pay.ttl-waiting", true, false,

                false, props);
        queueTTL = queue;

        return queue;

    }
    @Bean
    public Queue queueWaiting() {
        Queue queue = new Queue("q.pay.waiting", true, false, false);
        queueWait = queue;
        return queue;

    }

    @Bean public Exchange exchangeTTLWaiting() {
        DirectExchange exchange = new DirectExchange("ex.pay.ttl-waiting", true, false);
        exchangeTTL = exchange;
        return exchange; }

    /** * 该交换器使用的时候，需要给每个消息设置有效期 * @return */
    @Bean
    public Exchange exchangeWaiting() {
        DirectExchange exchange = new DirectExchange("ex.pay.waiting", true, false);
        exchangeWait = exchange;
        return exchange; }

    @Bean
    public Binding bindingTTLWaiting() {
        return BindingBuilder
                .bind(queueTTL)
                .to(exchangeTTL)
                .with("pay.ttl-waiting").noargs(); }

    @Bean
    public Binding bindingWaiting() {
        return BindingBuilder
                .bind(queueWait)
                .to(exchangeWait)
                .with("pay.waiting").noargs(); }



}
