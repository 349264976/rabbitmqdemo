package com.powernode.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {
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
