package com.galaxy.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lane
 * @date 2021年08月21日 下午4:28
 */
@Configuration
public class RabbitConfiguration {

    @Bean
    public com.rabbitmq.client.ConnectionFactory rabbitFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitFactory.setHost("mha");
        rabbitFactory.setVirtualHost("/");
        rabbitFactory.setUsername("root");
        rabbitFactory.setPassword("1234");
        rabbitFactory.setPort(5672);
        return rabbitFactory;

    }

    @Bean
    public ConnectionFactory connectionFactory(com.rabbitmq.client.ConnectionFactory rabbitFactory) {
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitFactory);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory factory) {

        AmqpAdmin amqpAdmin = new RabbitAdmin(factory);

        return amqpAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {

        Queue myqueue = new Queue("myqueue");

        return myqueue;
    }

}