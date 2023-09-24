package com.example.rabbitdirect.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myrabbit")
public class RabbitConfig {

    //交换机名字
    @Value("${myrabbit.exchangeName}")
    private String exchangeName;
    //队列A名字
    @Value("${myrabbit.queueAName}")
    private String queueAName;
    //队列B名字
    @Value("${myrabbit.queueBName}")
    private String queueBName;

    /**
     * 定义交换机
     * @return
     */
    @Bean

    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue directQueueA(){
        return QueueBuilder.durable(queueAName).build();
    }

    @Bean
    public Queue directQueueB(){
        return QueueBuilder.durable(queueBName).build();
    }

    /**
     * 绑定队列与交换机
     */

    @Bean
    public Binding bingdirectQueueA(DirectExchange directExchange,Queue directQueueA){

        return BindingBuilder.bind(directQueueA).to(directExchange).with("warning");
    }

    @Bean
    public Binding bingdirectQueueB(DirectExchange directExchange,Queue directQueueB){

        return BindingBuilder.bind(directQueueB).to(directExchange)
                .with("error");
    }

    @Bean
    public Binding bingdirectQueueB1(DirectExchange directExchange,Queue directQueueB){

        return BindingBuilder.bind(directQueueB).to(directExchange)
                .with("info");
    }

    @Bean
    public Binding bingdirectQueueB2(DirectExchange directExchange,Queue directQueueB){

        return BindingBuilder.bind(directQueueB).to(directExchange)
                .with("warning");
    }





}
