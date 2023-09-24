package com.powernode.service;

import com.powernode.config.MyConfirmCallBack;
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
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MyConfirmCallBack confirmCallBack;

    @PostConstruct //构造方法后执行它，相当于初始化作用
    public void init(){
        rabbitTemplate.setConfirmCallback(confirmCallBack);
    }

    public void sendMsg(){
        Message message= MessageBuilder.withBody("hello world".getBytes()).build();
        CorrelationData correlationData=new CorrelationData(); //关联数据
        correlationData.setId("order_123456"); //发送订单信息
        rabbitTemplate.convertAndSend("exchange.confirm.1","info",message,correlationData);
        log.info("消息发送完毕，发送时间为：{}",new Date());
    }
}
