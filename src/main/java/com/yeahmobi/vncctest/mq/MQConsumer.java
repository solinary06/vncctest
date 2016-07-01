package com.yeahmobi.vncctest.mq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Created by steven.liu on 2016/1/27.
 */
public class MQConsumer {
    public DefaultMQPushConsumer consumer;
    RmqAdmin admin = new RmqAdmin();

    static {
        System.setProperty("rocketmq.namesrv.addr", "172.30.30.125:9876");
        System.setProperty("enable_ssl", "true");
    }

    public void consumeMessage(String consumerGroup, String topic) {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        try {
            consumer.subscribe(topic, "*");

            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    System.out.print(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
                    String sendT = msgs.get(0).getProperty("sendT");
                    if (null != sendT) {
                        System.out.println(", MQ Time:" + (System.currentTimeMillis() - Long.parseLong(sendT)) + "ms");
                    } else {
                        System.out.println();
                    }

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void consumeMessage(String consumerGroup, String topic, MessageListenerConcurrently listener) {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        try {
            consumer.subscribe(topic, "*");

            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(listener);

            consumer.start();

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void shutdownConsumer(){
        this.consumer.shutdown();
    }

    public static void main(String[] args) {
        new MQConsumer().consumeMessage("test", "T_YM_REGULATION_OFFER");
    }
}
