package com.charles.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * @author charles
 * @date 2021/2/11 16:21
 */
public class HttpUtils {
    public static HttpClient getHttpClient() {
        return getHttpClient("utf-8");
    }

    public static HttpClient getHttpClient(String encoding) {
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset(encoding);
        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(5000);
        client.getParams().setSoTimeout(10000);
        return client;
    }

    public static String get(String url) {
        GetMethod method = new GetMethod(url);
        try {
            int status = getHttpClient().executeMethod(method);
            if (status == 200) {
                return method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public static String post(String url, Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Invalid number of parameters; each name must have a corresponding value!");
        }
        PostMethod method = new PostMethod(url);
        for (int i = 0; i < params.length; i += 2) {
            if (params[i] == null || params[i + 1] == null)
                continue;
            method.addParameter(params[i].toString(), params[i + 1].toString());
        }
        try {
            int status = getHttpClient().executeMethod(method);
            if (status == 200) {
                return method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return null;
    }
}
