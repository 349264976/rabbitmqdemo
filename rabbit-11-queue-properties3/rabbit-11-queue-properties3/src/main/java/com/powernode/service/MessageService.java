package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Value("${my.exchangeName}")
    private String exchangeName;

    public void sendMsg(){
        for (int i = 1; i <= 1000; i++) {
            String strMsg="hello world "+ i;
            Message message= MessageBuilder.withBody(strMsg.getBytes())
                    .build();
            rabbitTemplate.convertAndSend(exchangeName,"info",message);
        }

        log.info("消息发送完毕，发送时间为：{}",new Date());
    }
}
