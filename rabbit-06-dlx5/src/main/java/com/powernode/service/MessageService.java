package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        String str="hello world";
        Message message = MessageBuilder.withBody(str.getBytes())
                .build();
        rabbitTemplate.convertAndSend("exchange.normal.5", "order", message);

        log.info("消息发送完毕：发送时间为：{}", new Date());
    }
}
