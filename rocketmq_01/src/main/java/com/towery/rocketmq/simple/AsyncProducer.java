package com.towery.rocketmq.simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("producerGroup1");
        //指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");
        //启动生产者
        producer.start();
        //重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount=100;
        CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        for (int i = 0; i < messageCount; i++) {
            final int index=i;
            Message message = new Message("TopicTest", "TagA", ("异步：hello rocketmq"+i).getBytes(StandardCharsets.UTF_8));
            //发送消息
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %S %n",index,sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %S %n",index,throwable);
                    throwable.printStackTrace();
                }
            });

        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        //关闭生产者
        producer.shutdown();
    }
}
