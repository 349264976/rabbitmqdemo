package com.itkaien.rabbittopic.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    public TopicExchange topicExchange(){
        return ExchangeBuilder.topicExchange
        (exchangeName).build();
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue topicQueueA(){
        return QueueBuilder.durable(queueAName).build();
    }

    @Bean
    public Queue topicQueueB(){
        return QueueBuilder.durable(queueBName).build();
    }

    /**
     * 绑定队列与交换机
     */

    @Bean
    public Binding bingtopicQueueA(TopicExchange topicExchange,Queue topicQueueA){

        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("*.orange.*");
    }

    @Bean
    public Binding bingtopicQueueB(TopicExchange topicExchange,Queue topicQueueB){

        return BindingBuilder.bind(topicQueueB).to(topicExchange)
                .with("*.*.rabbit");
    }

    @Bean
    public Binding bingtopicQueueB1(TopicExchange topicExchange,Queue topicQueueB){

        return BindingBuilder.bind(topicQueueB).to(topicExchange)
                .with("lazy.#");
    }

//    @Bean
//    public Binding bingdirectQueueB2(DirectExchange directExchange,Queue directQueueB){
//
//        return BindingBuilder.bind(directQueueB).to(directExchange)
//                .with("warning");
//    }





}
