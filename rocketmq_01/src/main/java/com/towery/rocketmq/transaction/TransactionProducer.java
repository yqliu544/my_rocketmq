package com.towery.rocketmq.transaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        TransactionListenerImpl transactionListener = new TransactionListenerImpl();
        //创建生产者
        TransactionMQProducer producer = new TransactionMQProducer("my-producer-group1");
        //指定nameserver的地址
        producer.setNamesrvAddr("localhost:9876");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 10l, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("10");
            }
        });
        producer.setTransactionListener(transactionListener);
        producer.start();
        String[] tags=new String[]{"TagA","TagB","TagC","TagD","TagE"};
        for (int i = 0; i < 10; i++) {
            Message message = new Message("TransactionTest", tags[i% tags.length],"KEY"+i, ("hello rocketmq"+i).getBytes(StandardCharsets.UTF_8));
            //发送消息
            SendResult sendResult = producer.sendMessageInTransaction(message,null);
            System.out.println(sendResult);
            Thread.sleep(10);
        }
        //关闭生产者
        producer.shutdown();
    }
}
