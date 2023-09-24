package com.itkaien.rabbitmqresavemessage.Conteroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceviceMessage {
    //接收两个队列的消息
    @RabbitListener(queues = {"queue.fanout.A","queue.fanout.B"})
    public void receiveMessage(Message message){
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("Received message 接收成功:"+message);
        System.out.println(s);
    }

}
