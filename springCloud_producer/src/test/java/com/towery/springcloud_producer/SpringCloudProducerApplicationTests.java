package com.towery.springcloud_producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringCloudProducerApplicationTests {
    @Autowired
    private MyProducer myProducer;

    @Test
    void contextLoads() {
    }

    @Test
    void testSendMessage(){
        myProducer.sendMessage("你好spring cloud");
    }
}
