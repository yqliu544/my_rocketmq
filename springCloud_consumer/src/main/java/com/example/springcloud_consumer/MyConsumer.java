package com.example.springcloud_consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @StreamListener(Sink.INPUT)
    public void processMesage(String message){
        System.out.println(message);
    }
}
