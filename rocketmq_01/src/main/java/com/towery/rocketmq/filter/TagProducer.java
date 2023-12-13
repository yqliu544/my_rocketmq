package com.towery.rocketmq.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class TagProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("my-producer-group3");
        //指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");
        //启动生产者
        producer.start();
        String[] tags=new String[]{"Tag1","Tag2","Tag3"};
        for (int i = 0; i < 10; i++) {
            Message message = new Message("TagFilterTest", tags[i%tags.length], ("hello rocketmq"+i).getBytes(StandardCharsets.UTF_8));
            //发送消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        //关闭生产者
        producer.shutdown();
    }
}
