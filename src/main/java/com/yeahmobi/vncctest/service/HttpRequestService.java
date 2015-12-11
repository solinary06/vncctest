package com.yeahmobi.vncctest.service;

import com.yeahmobi.vncctest.dao.HbaseDAO;
import com.yeahmobi.vncctest.mq.QueryMessage;
import com.yeahmobi.vncctest.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by steven.liu on 2015/12/10.
 */
@Controller
public class HttpRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestService.class);
    private ModelAndView view;

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

    @RequestMapping(value = "/httpClick", method = RequestMethod.GET)
    public ModelAndView doClick(@RequestParam("clickServiceSelect") String server,
                                @RequestParam("apiSelect") String api,
                                @RequestParam("offerId") String offer,
                                @RequestParam("affId") String aff) {
        HttpUtil httpUtil = new HttpUtil();
        ModelAndView view = new ModelAndView("httpRequest");
        String url;
        if(api.equals("click")){
            url = server + "/trace?offer_id=" + offer +"&aff_id=" +aff;
        }else if(api.equals("offerInfo")){
            url = server + "/offerInfo?offer=" + offer +"&aff=" +aff;
        }else{
            view.addObject("msg", "Wrong api!");
            return view;
        }

        String message = httpUtil.doGet(url, null);
        String clickid = findClickId(message);
        if (!clickid.equals("")) {
            message = "Click succeed and transaction id is " + clickid;
            view.addObject("transId", "" + clickid);
        }

        view.addObject("msg", "" + message);
        return view;
    }

    @RequestMapping(value = "/httpConv", method = RequestMethod.GET)
    public ModelAndView doConversion(@RequestParam("convServiceSelect") String server,
                                @RequestParam("transactionId") String transactionId) {
        HttpUtil httpUtil = new HttpUtil();
        ModelAndView view = new ModelAndView("httpRequest");
        String url = server + "/conv?transaction_id=" + transactionId;

        String message = httpUtil.doGet(url, null);
        view.addObject("transId", "" + transactionId);
        view.addObject("msg", "" + message);
        return view;
    }

    @RequestMapping(value = "/queryMessage", method = RequestMethod.GET)
         public ModelAndView queryMessage(@RequestParam("messageTagSelect") String tag, @RequestParam("messageKey") String key) {
        QueryMessage queryMessage = new QueryMessage();
        ModelAndView view = new ModelAndView("httpRequest");

        String message = queryMessage.queryMessageByKey("T_YMREDIRECTOR_QUEUE_COLLECTOR_"+tag,key);

        view.addObject("transId", "" + key);
        view.addObject("msg", "" + message);
        return view;
    }

    @RequestMapping(value = "/queryTransIdInHbase", method = RequestMethod.GET)
    public ModelAndView queryTransIdInHbase(@RequestParam("hbaseTransId") String id) {
        HbaseDAO hbaseDAO = new HbaseDAO();
        ModelAndView view = new ModelAndView("httpRequest");

        String message = hbaseDAO.findByTransactionId("clicklog",id);

        view.addObject("transId", "" + id);
        view.addObject("msg", "" + message);
        return view;
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
}
