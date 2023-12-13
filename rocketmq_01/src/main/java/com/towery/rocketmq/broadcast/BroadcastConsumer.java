package com.towery.rocketmq.broadcast;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class BroadcastConsumer {
    public static void main(String[] args) throws MQClientException {
        //创建消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group1");
        //指明nameserver的地址
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置为广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //订阅主题
        consumer.subscribe("Borad_Topic","*");
        //创建一个监听器，当borker把消息推过来时调用
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("收到的消息："+new String(msg.getBody()));
                }
                //返回一个消费成功的
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
