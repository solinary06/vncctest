package com.yeahmobi.vncctest.mq;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MQVersion;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.protocol.RemotingCommand;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Iterator;

/**
 * Created by steven.liu on 2015/12/11.
 */
public class QueryMessage {

    private String queryByKey(DefaultMQAdminExt admin, String topic, String key) throws MQClientException, InterruptedException {
        String msgInfo ="";
        admin.start();
        QueryResult queryResult = admin.queryMessage(topic, key, 64, 0L, 9223372036854775807L);
        Iterator i$ = queryResult.getMessageList().iterator();

        while(i$.hasNext()) {
            MessageExt msg = (MessageExt)i$.next();
            msgInfo += "Message id: " + msg.getMsgId() + "<br>Message body: " + Bytes.toString(msg.getBody()) + "<br><br>";
        }

        return msgInfo;
    }

    public String queryMessageByKey(String topic, String key) {
        String message = "";
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, "172.30.30.125:9876");
        System.setProperty(RemotingCommand.RemotingVersionKey, Integer.toString(MQVersion.CurrentVersion));
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));

        try {
            message = this.queryByKey(defaultMQAdminExt, topic, key);
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            defaultMQAdminExt.shutdown();
        }
        return message;
    }

    public static void main(String[] args) {
        QueryMessage qm = new QueryMessage();
        String msg = qm.queryMessageByKey("T_YMREDIRECTOR_QUEUE_COLLECTOR_QA","fe0d3ee38-d144-9799-b9ea8e0b913d0f257a0dc8403a151f69ab00d314b12000c");
        System.out.println(msg);
    }
}
