package com.yeahmobi.vncctest.mq;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;

import java.util.*;

/**
 * Created by steven.liu on 2016/5/10.
 */
public class RmqAdmin {

    static {
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, "172.30.30.125:9876");
        System.setProperty("enable_ssl", "true");
    }
    private DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();

    public RmqAdmin(){
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        try {
            start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void start() throws MQClientException{
        defaultMQAdminExt.start();
    }

    public void shutdown(){
        defaultMQAdminExt.shutdown();
    }

    public List<MessageExt> queryMessageByKey(String topic, String key) {

        try{
            QueryResult queryResult = defaultMQAdminExt.queryMessage(topic, key, 64, 0L, 9223372036854775807L);
            Iterator i$ = queryResult.getMessageList().iterator();

            List<MessageExt> msgList = new ArrayList<MessageExt>();

            while(i$.hasNext()) {
                MessageExt msg = (MessageExt)i$.next();
                msgList.add(msg);

            }
            return msgList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public MessageExt queryMessageById(String id) {
        try{
             return defaultMQAdminExt.viewMessage(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void resetOffsetByTime(String consumerGrop, String topic, long timestamp) {

        if(timestamp <= 0) {
            timestamp = System.currentTimeMillis();
        }
        try {
            defaultMQAdminExt.resetOffsetByTimestamp(topic, null, consumerGrop, timestamp, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());


        RmqAdmin admin = new RmqAdmin();
//        admin.resetOffsetByTime("test","T_YM_REGULATION_OFFER",0);
//        List<MessageExt> list = admin.queryMessageByKey("T_YM_REGULATION_OFFER", "118743");
//        for (MessageExt messageExt : list) {
//            System.out.println(messageExt.getMsgId());
//        }
        System.out.println(new String(admin.queryMessageById("AC1E1E7D00002A9F00000071334293C6").getBody()));

        admin.shutdown();
    }

}
