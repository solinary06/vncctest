package com.yeahmobi.vncctest.service;

import com.alibaba.fastjson.JSONObject;
import com.yeahmobi.vncctest.dao.RedisDao;
import com.yeahmobi.vncctest.mq.MQProducer;
import com.yeahmobi.vncctest.mq.QueryMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven.liu on 2016/6/21.
 */
@Controller
public class ManageDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageDataService.class);
    Map<String, String> topicMap = new HashMap<String, String>();

    MQProducer producer = new MQProducer();
    @Autowired
    RedisDao redisDAO;

    static {
        System.setProperty("rocketmq.namesrv.addr", "172.30.30.125:9876");
        System.setProperty("enable_ssl", "true");
    }

    public ManageDataService() {
        topicMap.put("offer", "T_YM_REGULATION_OFFER");
        topicMap.put("affiliate", "T_YM_REGULATION_AFFILIATE");
        topicMap.put("app", "T_YM_REGULATION_APP");
        topicMap.put("personalizedOffer", "T_YM_REGULATION_OFFER_AFFILIATE");
        topicMap.put("appOffer", "T_YM_REGULATION_OFFER_APP");

    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ModelAndView showDataPage() {
        return new ModelAndView("manageData");
    }

    @RequestMapping(value = "/createData", method = RequestMethod.POST)
    public
    @ResponseBody
    String createData(@RequestParam("data") String data,
                      @RequestParam("type") String type,
                      @RequestParam("environment") String environment) {

        String tag = "";
        String topic = topicMap.get(type);
        if (environment.equals("qa")) {

            tag = "qa";
        } else if (environment.equals("dev")) {
            System.setProperty("rocketmq.namesrv.addr", "172.30.30.125:9876");
            tag = "dev";
        } else if (environment.equals("ali")) {
            System.setProperty("rocketmq.namesrv.addr", "172.30.30.125:9876");
            tag = "qa_auto_test";
        } else {
            return "Environment Error";
        }

        sendRegulationMessage(topic, tag, data);

        return "Succeed to create data.";
    }

    @RequestMapping(value = "/loadData", method = RequestMethod.POST)
    public
    @ResponseBody
    String loadData(@RequestParam("id") String id,
                    @RequestParam("type") String type,
                    @RequestParam("environment") String environment) {

        String data = redisDAO.getData(id,type,environment);

        JSONObject jsonObject = JSONObject.parseObject(data);
        data = jsonObject.get("value").toString();

        return data;
    }

    private void sendRegulationMessage(String topic, String tag, String json) {
        long timestamp = System.currentTimeMillis();
        json = "{\"update_time\":" + timestamp + ",\"after\":" + json + "}";

        producer.sendMessage(topic, tag, "vncctest", json);
    }
}


