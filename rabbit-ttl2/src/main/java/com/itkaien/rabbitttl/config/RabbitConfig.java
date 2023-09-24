package com.itkaien.rabbitttl.config;

import com.rabbitmq.client.impl.AMQImpl;
import java.util.HashMap;
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
    @Value("${myrabbit.queueName}")
    private String queueAName;
//    //队列B名字
//    @Value("${myrabbit.queueBName}")
//    private String queueBName;

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
        /**
         * 创建队列两种方式
         */
        HashMap<String, Object> argument = new HashMap<>();
        argument.put("x-message-ttl",15000);


        Queue queue = new Queue(queueAName, true, false, false, argument);

//        return QueueBuilder.durable(queueAName).build();

        return queue;
    }

//    @Bean
//    public Queue directQueueB(){
//        return QueueBuilder.durable(queueBName).build();
//    }

    /**
     * 绑定队列与交换机
     */

    @Bean
    public Binding bingdirectQueueA(DirectExchange directExchange,Queue directQueueA){

        return BindingBuilder.bind(directQueueA).to(directExchange).with("info");
    }


}
