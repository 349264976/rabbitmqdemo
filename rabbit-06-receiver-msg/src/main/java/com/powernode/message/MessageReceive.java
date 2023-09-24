package com.powernode.message;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class MessageReceive {
    @RabbitListener(queues = {"queue.normal.4"})
    public void receiveMsg(Message message, Channel channel) {
        //获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();
        //获取消息的唯一标识，类似身份证或者学号
        long deliveryTag = messageProperties.getDeliveryTag();

        try {
            byte[] body = message.getBody();
            String str = new String(body);
            log.info("接收到的消息为：{},接收时间为：{}", str, new Date());
            //TODO 业务逻辑处理
            int a = 1 / 0;
            //消费者的手动确认，false 只确认当前消息，改成true为批量确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("接收着出现问题：{}", e.getMessage());
            try {
                //消费者的手动不确认，参数3 是重新入队，
                //不会进入死信队列
//                channel.basicNack(deliveryTag, false, true);
                // 消费者的手动不确认，参数3 false 不重新入队（不重新投递），就会变成死信
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
