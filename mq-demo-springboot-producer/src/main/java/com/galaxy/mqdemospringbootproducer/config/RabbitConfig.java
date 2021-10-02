package com.galaxy.mqdemospringbootproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lane
 * @date 2021年08月21日 下午4:47
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("myqueue123",false,false,false,null);
    }

    @Bean
    public Exchange myExchange() {
        //交换器名称，交换器类型（），是否是持久化的，是否自动删除，交换器属性Map集合
//        new CustomExchange("custom.biz.ex", ExchangeTypes.DIRECT, false, false, null);
        return new DirectExchange("myex", false, false, null);
    }

    @Bean
    public Binding myBinding() {
        // 绑定的目的地，绑定的类型：到交换器还是到队列，交换器名称，路由key， 绑定的属性
        // new Binding("", Binding.DestinationType.EXCHANGE, "", "",null);
        // 绑定了交换器direct.biz.ex到队列myqueue，路由key是 direct.biz.ex
        return new Binding("myqueue123", Binding.DestinationType.QUEUE, "myex", "mykey", null);
    }


}
