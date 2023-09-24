package com.itkaien.rabbitttl.config.service;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * @param
     *param 1 交换机
     * param 2  路由
     * @param 3 消息
     * @return
     */

    public String  sendMessage(){

        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setExpiration("15000");

        Message build = MessageBuilder.withBody("messagehello".getBytes())
                .andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.direct.ttl","info",build);
        log.info("sendMessage successfully");
        System.out.println("sendMessage successfully");

        return "message successfully";
    }
}
