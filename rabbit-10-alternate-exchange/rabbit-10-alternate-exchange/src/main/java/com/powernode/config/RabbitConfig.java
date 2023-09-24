package com.powernode.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.exchangeAlternateName}")
    private String exchangeAlternateName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;

    @Value("${my.queueAlternateName}")
    private String queueAlternateName;

    @Bean
    public DirectExchange normalExchange(){
        return ExchangeBuilder // 默认为持久化的，默认不自动删除
                .directExchange(exchangeNormalName) // 交换机的名字
                .alternate(exchangeAlternateName) //设置备用交换机 alternate-exchange
                .build();
    }

    @Bean
    public Queue queueNormal(){
        return QueueBuilder.durable(queueNormalName).build();
    }

    @Bean
    public Binding binding(DirectExchange normalExchange,Queue queueNormal){
        return BindingBuilder.bind(queueNormal).to(normalExchange).with("info");
    }

    @Bean //备用交换机
    public FanoutExchange alternateExchange(){
        return ExchangeBuilder.fanoutExchange(exchangeAlternateName).build();
    }

    @Bean
    public Queue alternateQueue(){
        return QueueBuilder.durable(queueAlternateName).build();
    }

    @Bean
    public Binding bindingAlternate(FanoutExchange alternateExchange,Queue alternateQueue){
        return BindingBuilder.bind(alternateQueue).to(alternateExchange);
    }
}
