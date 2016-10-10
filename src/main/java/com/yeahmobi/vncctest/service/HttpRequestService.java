package com.yeahmobi.vncctest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yeahmobi.vncctest.dao.HbaseDAO;
import com.yeahmobi.vncctest.data.ResponseResult;
import com.yeahmobi.vncctest.encrypt.AppTypeEncryptUtil;
import com.yeahmobi.vncctest.encrypt.AppTypeEnum;
import com.yeahmobi.vncctest.mq.QueryMessage;
import com.yeahmobi.vncctest.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by steven.liu on 2015/12/10.
 */
@Controller
public class HttpRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestService.class);
    private ModelAndView view;
    HashMap<String, AppTypeEnum> appTypeMap = new HashMap<String, AppTypeEnum>();

    HttpRequestService(){
        appTypeMap.put("affiliate", AppTypeEnum.AFF);
        appTypeMap.put("sdk", AppTypeEnum.SDK);
        appTypeMap.put("offline", AppTypeEnum.OFF);
        appTypeMap.put("realtime", AppTypeEnum.REAL);
    }

    @RequestMapping(value = "/http", method = RequestMethod.GET)
    public ModelAndView showHttpView() {
        view = new ModelAndView("httpRequest");
        return view;
    }

    @RequestMapping(value = "/httpGet", method = RequestMethod.GET)
    public ModelAndView doHttpGet(@RequestParam("getUrl") String url) {
        HttpUtil httpUtil = new HttpUtil();
        String message = httpUtil.doGet(url, null);
        String clickid = findClickId(message);
        if (!clickid.equals("")) {
            message = "Click succeed and transaction id is " + clickid;
        }

        view.addObject("msg", "" + message);
        return view;
    }

    @RequestMapping(value = "/httpClick", method = RequestMethod.POST)
    public
    @ResponseBody
    String doClick(@RequestParam("clickServiceSelect") String server,
                   @RequestParam("apiSelect") String api,
                   @RequestParam("offerId") String offer,
                   @RequestParam("affId") String aff,
                   @RequestParam("appId") String app,
                   @RequestParam("type") String type,
                   @RequestParam("header") String header,
                   @RequestParam("params") String params) {
        HttpUtil httpUtil = new HttpUtil();
        String url;
        if (api.equals("click")) {

            if(type.equals("affiliate")){
                url = server + "/trace?offer_id=" + offer + "&aff_id=" + aff + params;
            }else{
                String encodedType = AppTypeEncryptUtil.encode(appTypeMap.get(type),Integer.parseInt(app),Integer.parseInt(offer));
                url = server + "/trace?offer_id=" + offer + "&app_id=" + app + "&type=" + encodedType +params;
            }
        } else if (api.equals("offerInfo")) {
            url = server + "/offerInfo?offer=" + offer + "&aff=" + aff + "&app=" + app;
        } else {
            return "wrong API!";
        }

        String message = "";
        if (!header.equals("")) {
            HashMap headerMap = toHashMap(header);
            message = httpUtil.doGet(url, headerMap);
        } else {
            message = httpUtil.doGet(url, null);
        }

        String clickid = findClickId(message);
        ResponseResult response = new ResponseResult();
        if (!clickid.equals("")) {
            message = "Click succeed and transaction id is " + clickid;
            response.setResult("success");
        } else if(api.equals("offerInfo")){
            response.setResult("success");
        } else {
            response.setResult("fail");
        }
        response.setData(message);
        String json =  JSON.toJSONString(response);

        return json;
    }

    @RequestMapping(value = "/httpConv", method = RequestMethod.POST)
    public
    @ResponseBody
    String doConversion(@RequestParam("convServiceSelect") String server,
                        @RequestParam("transactionId") String transactionId,
                        @RequestParam("header") String header,
                        @RequestParam("params") String params,
                        @RequestParam("api") String api
    ) {
        HttpUtil httpUtil = new HttpUtil();
        String url = server + "/" + api + "?transaction_id=" + transactionId + params;

        String message = "";
        if (!header.equals("")) {
            HashMap headerMap = toHashMap(header);
            message = httpUtil.doGet(url, headerMap);
        } else {
            message = httpUtil.doGet(url, null);
        }

        return message;
    }

    @RequestMapping(value = "/queryMessage", method = RequestMethod.POST)
    public @ResponseBody String queryMessage(@RequestParam("messageTagSelect") String tag,
                                             @RequestParam("messageKey") String key) {
        QueryMessage queryMessage = new QueryMessage();

        String message = queryMessage.queryMessageByKey("T_YMREDIRECTOR_QUEUE_DRUID_" + tag.toUpperCase(), key);

        return message;
    }

    @RequestMapping(value = "/queryTransIdInHbase", method = RequestMethod.POST)
    public @ResponseBody String queryTransIdInHbase(@RequestParam("hbaseTransId") String id) {
        HbaseDAO hbaseDAO = new HbaseDAO();

        String message = hbaseDAO.findByTransactionId("clicklog", id);

        return message;
    }

    @RequestMapping(value = "/doRegulation", method = RequestMethod.POST)
    public
    @ResponseBody
    String doRegulation(@RequestParam("server") String server,
                        @RequestParam("type") String type,
                        @RequestParam("time") String time) {
        HttpUtil httpUtil = new HttpUtil();
        String url = server + "/ymapi/OfferRegulation/" + type;

        if (time.equals("")) {
            time = null;
        }
        String message = httpUtil.doRegulationPost(url, time);
        return message;
    }

    private String findClickId(String redirectUrl) {
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

    private static HashMap<String, String> toHashMap(String str) {
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.parseObject(str);
        Iterator it = jsonObject.keySet().iterator();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }
}
