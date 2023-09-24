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
                .build();
    }

    @Bean
    public Queue queue(){
//        Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments)
        //autodelete 设置为true 自动删除
        return new Queue(queueName,true,false,true);
    }

    @Bean
    public Binding binding(DirectExchange directExchange,Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
