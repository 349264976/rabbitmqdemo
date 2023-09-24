package com.powernode.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Value("${my.exchangeNormalName}")
    private String exchangeNormalName;
    @Value("${my.queueNormalName}")
    private String queueNormalName;

    @Value("${my.exchangeDlxName}")
    private String exchangeDlxName;
    @Value("${my.queueDlxName}")
    private String queueDlxName;

    /**
     * 正常交换机 type=direct
     * @return
     */
    @Bean
    public DirectExchange normalExchange(){
        return ExchangeBuilder.directExchange(exchangeNormalName).build();
    }

    /**
     * 正常队列
     * @return
     */
    @Bean
    public Queue normalQueue(){
        Map<String, Object> arguments =new HashMap<>();
        arguments.put("x-message-ttl",15000); //设置对列的过期时间
        // 重点：：设置这两个参数
        //设置对列的死信交换机
        arguments.put("x-dead-letter-exchange",exchangeDlxName);
        //设置死信路由key，要和死信交换机和死信队列绑定key一模一样，因为死信交换机是直连交换机
        arguments.put("x-dead-letter-routing-key","error");
        return QueueBuilder.durable(queueNormalName)
                .withArguments(arguments) // 设置对列的参数
                .build();
    }

    /**
     * 正常交换机和正常队列绑定
     * @param normalExchange
     * @param normalQueue
     * @return
     */
    @Bean
    public Binding bindingNormal(DirectExchange normalExchange,Queue normalQueue){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("order");
    }

    /**
     * 死信交换机 type=direct
     * @return
     */
    @Bean
    public DirectExchange dlxExchange(){
        return ExchangeBuilder.directExchange(exchangeDlxName).build();
    }

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(queueDlxName).build();
    }

    /**
     * 死信交换机和死信队列绑定
     * @param dlxExchange
     * @param dlxQueue
     * @return
     */
    @Bean
    public Binding bindingDlx(DirectExchange dlxExchange,Queue dlxQueue){
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("error");
    }
}
