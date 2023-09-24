package com.powernode.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
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

    /**
     * 构造方法执行后自动执行
     */
    @PostConstruct
    public void init(){
        //开启生产者的确定模式
        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause)->{
                    if(!ack){
                        log.error("消息没有到达交换机，原因为：{}",cause);
                        //TODO 重发消息或者记录错误日志
                    }
                }
        );

        rabbitTemplate.setReturnsCallback(
                returnedMessage->{
                    log.error("消息没有从交换机正确的投递（路由）到队列，原因为：{}",returnedMessage.getReplyText());
                    //TODO 记录错误日志，给程序员发短信或者或者邮件
                }
        );
    }

    public void sendMsg(){
        MessageProperties messageProperties=new MessageProperties();
        //设置单条消息的持久化，默认就是持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message= MessageBuilder.withBody("hello world".getBytes())
                .andProperties(messageProperties).build();
        rabbitTemplate.convertAndSend("exchange.reliability","info",message);
        log.info("消息发送完毕，发送时间为：{}",new Date());
    }
}
