package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService implements RabbitTemplate.ReturnsCallback {
    @Resource
    private RabbitTemplate rabbitTemplate;



    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnsCallback(this); //设置回调
    }

    public void sendMsg(){
        Message message= MessageBuilder.withBody("hello world".getBytes())
                .build();
        rabbitTemplate.convertAndSend("exchange.return.2","info",message);
        log.info("消息发送完毕，发送时间为：{}",new Date());
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息从交换机没有正确的路由到（投递到）队列，原因为：{}",returnedMessage.getReplyText());
    }
}
