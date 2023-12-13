package com.towery.rocketmq.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrderProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        //指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");
        //启动生产者
        producer.start();
        for (int i = 0; i < 10; i++) {
            int orderId=i;
            for (int j = 0; j < 5; j++) {
                Message message = new Message("OrderTopicTest", "order_"+orderId, "KEY"+orderId,("顺序：hello rocketmq："+orderId+"step"+j).getBytes(StandardCharsets.UTF_8));
                //发送消息
                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        Integer id = (Integer) o;
                        int index = id % list.size();
                        return list.get(index);
                    }
                }, orderId);
                System.out.println(sendResult);
            }
        }
        //关闭生产者
        producer.shutdown();
    }
}
