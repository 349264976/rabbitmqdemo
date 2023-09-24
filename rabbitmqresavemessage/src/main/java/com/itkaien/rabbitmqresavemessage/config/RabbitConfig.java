//package com.itkaien.rabbitmqresavemessage.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//    //rabbitmq 配置三部曲
//    //1.定义交换机
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        //扇形交换机
//        return new FanoutExchange("exchange.fanout");
//    }
//    //2.定义队列
//    @Bean
//    public Queue queueA(){
//    return new Queue("queue.fanout.A");
//    }
//    @Bean
//    public Queue queueB(){
//        return new Queue("queue.fanout.B");
//    }
//    //3.绑定交换机与队列
//    @Bean
//    public Binding bindingA(FanoutExchange fanoutExchange,Queue queueA){
//        return BindingBuilder.bind(queueA).to(fanoutExchange);
//    }
//
//    @Bean
//    public Binding bindingB(FanoutExchange fanoutExchange,Queue queueB){
//        return BindingBuilder.bind(queueB).to(fanoutExchange);
//    }
//
//}
