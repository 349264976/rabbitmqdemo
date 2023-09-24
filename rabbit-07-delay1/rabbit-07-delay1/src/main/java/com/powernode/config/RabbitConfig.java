package com.powernode.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    @Bean
    public Queue queueNormal() {
        //方式1 new Queue 的方式
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 25000); //消息过期时间 25秒
//        return new Queue(queueName, true, false, false, arguments);
        //方式2 建造者
        return QueueBuilder
                .durable(queueNormalName) //队列名称
                .ttl(25000) //队列的过期时间
                .deadLetterExchange(exchangeName) //设置死信交换机 ，设置相同的交换机
                .deadLetterRoutingKey("error") //设置死信路由key，设置成交换机和死信队列绑定key
                .build();
    }

    @Bean
    public Binding bindingNormal(DirectExchange directExchange, Queue queueNormal) {
        return BindingBuilder.bind(queueNormal).to(directExchange).with("order");
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(queueDlxName).build();
    }

    @Bean
    public Binding bindingDlx(DirectExchange directExchange, Queue dlxQueue) {
        return BindingBuilder.bind(dlxQueue).to(directExchange).with("error");

    }

}
