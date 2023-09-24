package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(){
        //消息属性
        MessageProperties messageProperties=new MessageProperties();
        //设置单条消息的过期时间，单位为毫秒，数据类型为字符串
        messageProperties.setExpiration("15000");
        Message message= MessageBuilder.withBody("hello world".getBytes())
                .andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.normal.2","order",message);
        log.info("消息发送完毕：发送时间为：{}",new Date());

    }
}
