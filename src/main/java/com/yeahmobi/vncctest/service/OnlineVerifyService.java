package com.yeahmobi.vncctest.service;

import com.yeahmobi.vncctest.data.ResponseResult;
import com.yeahmobi.vncctest.data.VerifyData;
import com.yeahmobi.vncctest.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by steven.liu on 2016/7/6.
 */
@Controller
public class OnlineVerifyService {

    HttpUtil httpUtil = new HttpUtil();

    private String generateQueryTemplate(String reportId, long startTimeStamp,
                                         long endTimeStamp, String transaction_id) {
        return "{\n" +
                "    \"settings\": {\n" +
                "        \"pagination\": {\n" +
                "            \"size\": 10000,\n" +
                "            \"page\": 0\n" +
                "        },\n" +
                "        \"report_id\": \"" + reportId + "\",\n" +
                "        \"time\": {\n" +
                "            \"timezone\": \"8\",\n" +
                "            \"start\": " + startTimeStamp + ",\n" +
                "            \"end\": " + endTimeStamp + "\n" +
                "        },\n" +
                "        \"return_format\": \"json\",\n" +
                "        \"data_source\": \"ymds_druid_datasource\"\n" +
                "    },\n" +
                "    \"data\": [\n" +
                "        \"click\",\n" +
                "        \"conversion\"\n" +
                "    ],\n" +
                "    \"filters\": {\n" +
                "        \"$and\": {\n" +
                "            \"datasource\": {\n" +
                "                \"$eq\": \"Yeahmobi2\"\n" +
                "            },\n" +
                "            \"status\": {\n" +
                "                \"$nin\": [\n" +
                "                    \"Accepted\",\n" +
                "                    \"Dropped\",\n" +
                "                    \"Rejected\",\n" +
                "                    \"Failed\",\n" +
                "                    \"Fatal\"\n" +
                "                ]\n" +
                "            },\n" +
                "            \"transaction_id\":{\n" +
                "              \"$in\":[\"" + transaction_id + "\"]\n" +
                "            }" +
                "        }\n" +
                "    },\n" +
                "    \"group\": [\n" +
                "        \"aff_id\",\n" +
                "        \"offer_id\",\n" +
                "        \"transaction_id\"\n" +
                "    ]\n" +
                "}";
    }

    @RequestMapping(value = "/onlineVerify", method = RequestMethod.GET)
    public
    @ResponseBody
    String createData(@QueryParam("domain") String domain,
                      @QueryParam("offerId") String offerId,
                      @QueryParam("affId") String affId) {

        ResponseResult result = new ResponseResult();
        VerifyData data = new VerifyData();

        String url = "http://" + domain + "/trace?offer_id=" + offerId + "&aff_id=" + affId;

        String message = httpUtil.doGet(url, null);

        if (!findClickId(message).equals("")) {
            String transactionId = findClickId(message);
            result.setResult("success");
            data.setTransactionId(transactionId);
            result.setData(data);

        } else {
            result.setResult("fail");
            result.setData("Click failed");
        }

        String json = JSON.toJSONString(result);

        return json;
    }

    public String queryDruidData(String transaction_id) {

        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        long endTime = cal.getTime().getTime()/1000;
        cal.add(Calendar.HOUR_OF_DAY, -1);
        long startTime = cal.getTime().getTime()/1000;


        String queryParam = generateQueryTemplate(Long.toString(System.currentTimeMillis()),
                startTime, endTime, transaction_id);

        queryParam = queryParam.replaceAll("\n", "").replaceAll(" ", "")
                .replaceAll("}", "%7d").replaceAll("\\{", "%7b")
                .replaceAll("]", "%5d").replaceAll("\\[", "%5b")
                .replaceAll(",", "%2c").replaceAll(":", "%3a").replaceAll("\"", "%22");

//        String response = httpUtil.doGet("http://172.30.10.105:8080/impala/report?report_param=" + queryParam,null);
        String response = httpUtil.doGet("http://resin-yeahmobi-214401877.us-east-1.elb.amazonaws.com:18080/report/report?report_param=" + queryParam,null);

        return response;

    }

    public String findClickId(String redirectUrl) {
        if (redirectUrl == null || redirectUrl.equals("")) {
            return "";
        }
        String[] tmp = redirectUrl.split("\\?|\\&|=|\"");
        for (int i = 0; i < tmp.length; i++) {
            String clickId = tmp[i];
            if (clickId.length() == 67 && clickId.matches("[[a-f0-9]\\-]{67}")) {
                System.out.println("[ClickId] " + clickId);
                return clickId;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        OnlineVerifyService service = new OnlineVerifyService();
        HttpUtil httpUtil = new HttpUtil();
//        service.queryDruidData("fd9398353-6665-e441-b97fa0440ffd0576485e55de233c2724aac36eb38c90026");

    }
}
