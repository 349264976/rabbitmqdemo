package com.powernode.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> arguments =new HashMap<>();
//        arguments.put("x-overflow","reject-publish"); //队列的溢出行为，删除头部，改成拒绝发布
//        arguments.put("x-max-length",5); //队列的最大长度
        arguments.put("x-single-active-consumer",true); //单一消费者
        return new Queue(queueName,true,false,false,arguments);
    }

    @Bean
    public Binding binding(DirectExchange directExchange,Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("info");
    }
}
