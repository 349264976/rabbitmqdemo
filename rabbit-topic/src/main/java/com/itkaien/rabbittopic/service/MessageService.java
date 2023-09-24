package com.itkaien.rabbittopic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param
     *param 1 交换机
     * param 2  路由
     * @param 3 消息
     * @return
     */
    public String  sendMessage(){
        Message build = MessageBuilder.withBody("messagehellotopic1".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.Topic","a.la.rabbit",build);
        log.info("sendMessage successfully");
        System.out.println("sendMessage successfully");
        return "message successfully";
    }
}
