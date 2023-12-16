package com.towery.springboot_producer.producer;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic,String message){
        rocketMQTemplate.convertAndSend(topic,message);
    }
    //发送事务消息
    public void sendMessageInTransaction(String topic,String message) throws InterruptedException {
        String[] tags=new String[]{"TagA","TagB","TagC","TagD"};
        for (int i = 0; i < 10; i++) {
            Message<String> msg = MessageBuilder.withPayload(message).setHeader("a", "bbb").build();
            String destination=topic+":"+tags[i% tags.length];
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(destination, msg, destination);
            System.out.println(transactionSendResult);
            Thread.sleep(10);
        }
    }
}
