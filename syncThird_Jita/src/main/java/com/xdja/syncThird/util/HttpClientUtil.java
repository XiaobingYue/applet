package com.xdja.syncThird.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yxb on  2018/3/12
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final int CONNECT_TIMEOUT = 5000;

    private static final int REQUEST_TIMEOUT = 5000;

    private static final int SOCKET_TIMEOUT = 500000;

    private static final String CHARSET_UTF8 = "utf-8";

    public static String httpPostJson(String url, String data) {

        String retResult = "";
        HttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Connection", "close");
        httpPost.setHeader("Content-Type", "application/json");
        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new StringEntity(data, CHARSET_UTF8));
            httpResponse = client.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return "";
            }

            retResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
            EntityUtils.consumeQuietly(httpResponse.getEntity());
        }
        return retResult;
    }

    public static String httpGet(String url) {

        String retResult = "";
        HttpClient client = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Connection", "close");
        HttpResponse httpResponse = null;
        try {

            httpResponse = client.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return "";
            }
            retResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
            EntityUtils.consumeQuietly(httpResponse.getEntity());
        }
        log.info("result:{}", retResult);
        return retResult;
    }
}
