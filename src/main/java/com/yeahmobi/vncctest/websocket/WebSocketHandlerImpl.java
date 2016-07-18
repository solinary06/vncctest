package com.yeahmobi.vncctest.websocket;

/**
 * Created by steven.liu on 2016/7/8.
 */

import com.alibaba.fastjson.JSON;
import com.yeahmobi.vncctest.data.ResponseResult;
import com.yeahmobi.vncctest.data.VerifyData;
import com.yeahmobi.vncctest.data.VerifyInfo;
import com.yeahmobi.vncctest.service.OnlineVerifyService;
import com.yeahmobi.vncctest.util.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class WebSocketHandlerImpl implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(WebSocketHandlerImpl.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    HttpUtil httpUtil = new HttpUtil();
    OnlineVerifyService onlineVerifyService = new OnlineVerifyService();

    //初次链接成功执行
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("链接成功......");
        users.add(session);
        String userName = (String) session.getAttributes().get("username");
        logger.info(userName);
    }

    //接受消息处理消息
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String userName = (String) webSocketSession.getAttributes().get("username");
        String[] info = webSocketMessage.getPayload().toString().split(",");
        String domain = info[0];
        String offer = info[1];
        String aff = info[2];

        String port = domain.contains("com")?"":":8080";

        String url = "http://" + domain + port + "/trace?offer_id=" + offer + "&aff_id=" + aff;

        ResponseResult result = new ResponseResult();
        VerifyInfo data = new VerifyInfo();

        result.setResult("success");
        data.setPhase("click");
        data.setStatus("verifying");
        data.setInfo("Click is on progress, please wait.");
        result.setData(data);

        //send start click message to front-end
        sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));

        String message = httpUtil.doGet(url, null);
        wait(1);
        String transactionId = onlineVerifyService.findClickId(message);
        if (!transactionId.equals("")) {
            //send transaction id to front-end
            data.setInfo("Verify click result in druid, please wait, Transaction id is " + transactionId);
            result.setData(data);
            sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));

            wait(3);
            String druidResult = onlineVerifyService.queryDruidData(transactionId);

            if(druidResult.contains("1,0")){
                data.setPhase("click");
                data.setStatus("complete");
                data.setInfo("Verify click successfully");
                result.setData(data);
                sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));

                wait(1);

                data.setPhase("conversion");
                data.setStatus("verifying");
                data.setInfo("Conversion is on progress, please wait.");
                result.setData(data);
                sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));

                url = "http://" + domain + port + "/conv?transaction_id=" + transactionId;

                httpUtil.doGet(url, null);

                wait(1);
                data.setInfo("Verify conversion result in druid, please wait.");
                result.setData(data);
                sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));

                wait(3);

                druidResult = onlineVerifyService.queryDruidData(transactionId);


                if(druidResult.contains("1,1")){
                    data.setPhase("conversion");
                    data.setStatus("complete");
                    data.setInfo("Verify conversion successfully.");
                    result.setData(data);
                    sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));
                }else{
                    result.setResult("fail");
                    data.setPhase("conversion");
                    data.setStatus("complete");
                    data.setInfo("Verify conversion failed in druid, transaction id is " + transactionId + ", please check");
                    sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));
                }


            }else{
                result.setResult("fail");
                data.setPhase("click");
                data.setStatus("complete");
                data.setInfo("Verify click failed in druid, transaction id is " + transactionId + ", please check");
                sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));
            }

        } else {
            result.setResult("fail");
            data.setPhase("click");
            data.setStatus("verifying");
            data.setInfo("No click id found, click result : " + message);
            result.setData(data);

            //send click fail message to front-end
            sendMessageToUsers(new TextMessage(JSON.toJSONString(result)));
        }
    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        logger.info("链接出错，关闭链接......");
        users.remove(webSocketSession);
    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("链接关闭......" + closeStatus.toString());
        users.remove(webSocketSession);
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("username").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void wait(int seconds){
        try {
            Thread.sleep(seconds*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}