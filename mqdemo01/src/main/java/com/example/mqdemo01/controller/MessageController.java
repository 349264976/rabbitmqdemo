package com.example.mqdemo01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/messages")
    public String sendmessage(){
        messageService.sendMessage();
        return "send successfully";
    }



}
