package com.yeahmobi.vncctest.mq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by steven.liu on 2016/1/26.
 */
public class MQProducer {

    private DefaultMQProducer producer;

    public MQProducer(){
        this.producer = new DefaultMQProducer("automation_test");
        try {
            producer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String topic, String tag, String key, String body) {
        try {

            Message clickMsg = new Message(topic, tag, key, (body).getBytes());

            producer.send(clickMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void shutdown(){
        this.producer.shutdown();
    }
}
