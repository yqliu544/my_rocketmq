package com.towery.springcloud_producer;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Component
public class MyProducer {

    @Resource
    private Source source;

    public void sendMessage(String message){
        Map<String, Object> hashCode=new HashMap<>();
        hashCode.put(MessageConst.PROPERTY_TAGS,"Tag11");
        MessageHeaders messageHeaders=new MessageHeaders(hashCode);
        Message<String> message1 = MessageBuilder.createMessage(message, messageHeaders);
        source.output().send(message1);
    }

}
