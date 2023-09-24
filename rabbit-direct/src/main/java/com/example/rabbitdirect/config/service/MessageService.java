package com.example.rabbitdirect.config.service;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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
        Message build = MessageBuilder.withBody("messagehello".getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.direct","info",build);
        log.info("sendMessage successfully");
        System.out.println("sendMessage successfully");

        return "message successfully";
    }
}
