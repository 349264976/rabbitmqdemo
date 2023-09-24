package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService implements RabbitTemplate.ConfirmCallback{
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct //构造方法后执行它，相当于初始化作用
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(){
        Message message= MessageBuilder.withBody("hello world".getBytes()).build();
        CorrelationData correlationData=new CorrelationData(); //关联数据
        correlationData.setId("order_123456"); //发送订单信息
        rabbitTemplate.convertAndSend("exchange.confirm.2","info",message,correlationData);
        log.info("消息发送完毕，发送时间为：{}",new Date());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("关联id为：{}",correlationData.getId()+"");
        if (ack){
            log.info("消息正确的达到交换机");
            return;
        }
        //ack =false 没有到达交换机
        log.error("消息没有到达交换机，原因为：{}",cause);

    }
}
