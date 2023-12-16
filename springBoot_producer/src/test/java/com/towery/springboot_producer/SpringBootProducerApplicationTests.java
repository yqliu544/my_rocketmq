package com.towery.springboot_producer;

import com.towery.springboot_producer.producer.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootProducerApplicationTests {
    @Autowired
    private MyProducer myProducer;

    @Test
    void contextLoads() {
    }
    @Test
    void testSendMessage(){
        String topic="my-boot-topic";
        String message="我是李四。你是张三。";
        myProducer.sendMessage(topic,message);
        System.out.println("消息发送成功！"
        );
    }

    @Test
    void testSendMessageInTransaction() throws InterruptedException {
        String topic="my-boot-topic";
        String message="我是李四。你是张三。事务消息";
        myProducer.sendMessageInTransaction(topic,message);
        System.out.println("消息发送成功！"
        );
    }

}
