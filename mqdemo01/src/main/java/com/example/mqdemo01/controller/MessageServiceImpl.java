package com.example.mqdemo01.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage() {
        String mag="sendMessageFanout";
        Message message=new Message(mag.getBytes());

    rabbitTemplate.convertAndSend("exchange.fanout"
    ,"",message);
    log.info("Sent message successfully");
    }
}
