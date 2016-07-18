package com.yeahmobi.vncctest.util;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by steven.liu on 2015/12/9.
 */
public class HttpUtil {

    private final static int connTimeout = 3000; // 3s
    private final static int socketTimeout = 60000; // 60s
    private static final DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static int statusCode = 200;

    private static final String OFFER_REGULATION_T = "t";
    private static final String OFFER_REGULATION_ID = "id";
    private static final String OFFER_REGULATION_KEY = "key";
    private static final String OFFER_REGULATION_ID_VAL = "cc";
    private static final String OFFER_REGULATION_SALT_VAL = "Yeahmobif3899843bc09ff972ab6252ab3c3cac6";


    public String doRegulationPost(String url, String time) {
        Map parmMap = new HashMap();
        if (time == null) {
            time = DATE_FORMATER.format(new Date());
        }
        parmMap.put(OFFER_REGULATION_T, time);
        parmMap.put(OFFER_REGULATION_ID, OFFER_REGULATION_ID_VAL);
        parmMap.put(OFFER_REGULATION_KEY, MD5Util.encode(time + OFFER_REGULATION_SALT_VAL + OFFER_REGULATION_ID_VAL));

        return doPsot(url, parmMap, null);
    }

    public String doPsot(String url, Map<String, String> parms, Map<String, String> headers) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, connTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout * 6);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        client.setRedirectStrategy(new DefaultRedirectStrategy());

        BufferedReader bReader = null;
        InputStream inStream = null;
        String res = null;
        boolean ok = false;
        try {

            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if (parms != null) {
                for (String key : parms.keySet()) {
                    params.add(new BasicNameValuePair(key, parms.get(key)));
                }
            }
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = client.execute(httpPost);


            statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                inStream = response.getEntity().getContent();
                bReader = new BufferedReader(new InputStreamReader(
                        inStream));

                StringBuilder strBuilder = new StringBuilder();
                while (true) {
                    String line = bReader.readLine();
                    if (null == line) {
                        break;
                    }
                    strBuilder.append(line);
                }
                res = strBuilder.toString();
                ok = true;
            }
        } catch (Exception e) {
            LOGGER.error(url, e);
        } finally {
            try {
                if (null != bReader) {
                    bReader.close();
                }
                if (null != inStream) {
                    inStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(url, e);
            }
            client.getConnectionManager().shutdown();
            if (!ok) {
                LOGGER.error(url + ",code=" + statusCode);
            }
        }

        return res;
    }

    public String doGet(String url, HashMap<String, String> headers) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, connTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout * 6);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        client.setRedirectStrategy(new RedirectStrategy() {    //设置重定向处理方式
            public boolean isRedirected(HttpRequest arg0,
                                        HttpResponse arg1, HttpContext arg2)
                    throws ProtocolException {

                return false;
            }

            public HttpUriRequest getRedirect(HttpRequest arg0,
                                              HttpResponse arg1, HttpContext arg2)
                    throws ProtocolException {

                return null;
            }
        });

        BufferedReader bReader = null;
        InputStream inStream = null;
        String res = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key));
                }
            }

            HttpResponse response = client.execute(httpGet);

            statusCode = response.getStatusLine().getStatusCode();
//            if (200 == statusCode|| 302 == statusCode) {
            inStream = response.getEntity().getContent();
            bReader = new BufferedReader(new InputStreamReader(
                    inStream));

            StringBuilder strBuilder = new StringBuilder();
            while (true) {
                String line = bReader.readLine();
                if (null == line) {
                    break;
                }
                strBuilder.append(line);
            }
            res = strBuilder.toString();
//            }


        } catch (Exception e) {
            LOGGER.error(url, e);
            e.printStackTrace();
        } finally {
            try {
                if (null != bReader) {
                    bReader.close();
                }
                if (null != inStream) {
                    inStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(url, e);
            }
            client.getConnectionManager().shutdown();

        }

        return res;
    }

    public static void main(String[] args) {
//        HttpUtil httpUtil = new HttpUtil();
//        System.out.println(httpUtil.doGet("http://172.30.10.174:8080/check", null));
//        System.out.println(httpUtil.doRegulationPost("http://api.pre.dy/ymapi/OfferRegulation/updatedOfferInfo", "2015-12-09 00:20:02"));

        final ArrayList<String> list = new ArrayList<String>();
        Collection<String> stringValues = Arrays.asList("hello", "Guava",
                "Hello", "java");

        Collection<String> resultValues = Collections2.transform(stringValues, new Function<String, String>() {
            public String apply(String input) {
                list.add(input);
                System.out.print("11111");
                return input.toUpperCase();
            }
        });

        for(String str:list){
            System.out.println(str);
        }

        for(String str:resultValues){
            System.out.println(str);
        }

    }

}
