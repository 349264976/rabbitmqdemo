package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService{
    @Resource
    private RabbitTemplate rabbitTemplate;



    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnsCallback(
                //使用lambda表达式
                message->{
                    log.error("消息从交换机没有正确的路由到（投递到）队列，原因为：{}",message.getReplyText());
                }

        ); //设置回调
    }

    public void sendMsg(){
        Message message= MessageBuilder.withBody("hello world".getBytes())
                .build();
        rabbitTemplate.convertAndSend("exchange.return.4","info",message);
        log.info("消息发送完毕，发送时间为：{}",new Date());
    }

}
