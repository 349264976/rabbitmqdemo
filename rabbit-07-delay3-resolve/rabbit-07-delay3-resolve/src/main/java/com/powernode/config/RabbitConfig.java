package com.powernode.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeName}")
    private String exchangeName;
    @Value("${my.queueOrderName}")
    private String queueOrderName;

    @Value("${my.queuePayName}")
    private String queuePayName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(exchangeName).build();
    }

    @Bean
    public Queue queueOrderNormal() {
        //方式1 new Queue 的方式
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 25000); //消息过期时间 25秒
//        return new Queue(queueName, true, false, false, arguments);
        //方式2 建造者
        return QueueBuilder
                .durable(queueOrderName) //队列名称
                .deadLetterExchange(exchangeName) //设置死信交换机 ，设置相同的交换机
                .deadLetterRoutingKey("error") //设置死信路由key，设置成交换机和死信队列绑定key
                .build();
    }

    @Bean
    public Binding bindingOrderNormal(DirectExchange directExchange, Queue queueOrderNormal) {
        return BindingBuilder.bind(queueOrderNormal).to(directExchange).with("order");
    }

    @Bean
    public Queue queuePayNormal() {
        //方式1 new Queue 的方式
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-message-ttl", 25000); //消息过期时间 25秒
//        return new Queue(queueName, true, false, false, arguments);
        //方式2 建造者
        return QueueBuilder
                .durable(queuePayName) //队列名称
                .deadLetterExchange(exchangeName) //设置死信交换机 ，设置相同的交换机
                .deadLetterRoutingKey("error") //设置死信路由key，设置成交换机和死信队列绑定key
                .build();
    }

    @Bean
    public Binding bindingPayNormal(DirectExchange directExchange, Queue queuePayNormal) {
        return BindingBuilder.bind(queuePayNormal).to(directExchange).with("pay");
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
