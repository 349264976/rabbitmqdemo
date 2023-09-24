package com.powernode.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueName}")
    private String queueName;

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder // 默认为持久化的
                .directExchange(exchangeName) // 交换机的名字
                .durable(false) //不持久化，服务器重启会丢失
                .build();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding binding(DirectExchange directExchange,Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
