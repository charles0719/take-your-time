package com.charles.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * @author Andy.zuo
 * 适合多线程的HttpClient,用httpClient4.2.6实现
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {


    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 128;
    /**
     * 每个路由最大连接数,对于同一个目标机器的最大并发连接只有默认只有2个
     * 哪怕你设置连接池的最大连接数为200，但是实际上还是只有2个连接在工作，
     * 其他剩余的198个连接都在等待，都是为别的目标机器服务的（目标服务器通常指同一台服务器或者同一个域名）
     */
    public final static int MAX_ROUTE_CONNECTIONS = 32; //1000;//100  除以个负载数
    /**
     * 连接超时时间 10s
     */
    public final static int CONNECT_TIMEOUT = 15000;
    /**
     * 读取超时时间 5s
     */
    public final static int READ_TIMEOUT = 60000;

    public final static int HTTP_PORT = 80;
    public final static int HTTPS_PORT = 443;

    //    private static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";//IE6
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";
    private static final String CHARSET = "UTF-8";
    public static HttpClient httpClient;

    static {
        createClient();
    }

    public static HttpClient createClient() {
        if (httpClient == null) {
            httpClient = new SystemDefaultHttpClient();
            configureClient(httpClient);
        }
        return httpClient;
    }

    private static void configureClient(HttpClient httpClient) {
        // HTTP 协议的版本,1.1/1.0/0.9
        HttpProtocolParams.setVersion(httpClient.getParams(), HttpVersion.HTTP_1_1);
        // 字符集
        HttpProtocolParams.setContentCharset(httpClient.getParams(), CHARSET);
        //伪装的浏览器类型
        HttpProtocolParams.setUserAgent(httpClient.getParams(), USER_AGENT);
        HttpProtocolParams.setUseExpectContinue(httpClient.getParams(), true);
        //设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), CONNECT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), READ_TIMEOUT);

        //设置访问协议
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("http", HTTP_PORT, PlainSocketFactory.getSocketFactory()));
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", HTTPS_PORT, SSLSocketFactory.getSocketFactory()));

        if (httpClient.getConnectionManager() instanceof ThreadSafeClientConnManager) {
            ThreadSafeClientConnManager mgr = (ThreadSafeClientConnManager) httpClient.getConnectionManager();
            mgr.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        } else if (httpClient.getConnectionManager() instanceof PoolingClientConnectionManager) {
            PoolingClientConnectionManager mgr = (PoolingClientConnectionManager) httpClient.getConnectionManager();
            mgr.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        }

        if (httpClient.getConnectionManager() instanceof ThreadSafeClientConnManager) {
            ThreadSafeClientConnManager mgr = (ThreadSafeClientConnManager) httpClient.getConnectionManager();
            mgr.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        } else if (httpClient.getConnectionManager() instanceof PoolingClientConnectionManager) {
            PoolingClientConnectionManager mgr = (PoolingClientConnectionManager) httpClient.getConnectionManager();
            mgr.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        }

//		final HttpClient hc = httpClient;
//		executor = Executors.newSingleThreadScheduledExecutor();
//		executor.scheduleAtFixedRate(new Runnable(){
//			@Override
//			public void run() {//每分钟执行，处理各类任务
//				//logger.debug("httpclient4.2.6 close connections at " + DateUtil.getDateTime());
//				hc.getConnectionManager().closeExpiredConnections();//关闭连接失效的链接
//				hc.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);//关闭30s空闲的链接
//			}
//		}, 30, 30, TimeUnit.SECONDS);
    }


    public static byte[] doGet(String url, String params) throws Exception {
        HttpGet get = new HttpGet(url + (params == null ? "" : params));
        get.setHeader("Referer", "http://fund.eastmoney.com/data/fundranking.html");
        try {
            HttpResponse response = httpClient.execute(get);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                if (response.getEntity() != null) {
                    return EntityUtils.toByteArray(response.getEntity());
                }
            } else {
                get.abort();//如果不想获取HTTPClient返回的信息  断开连接
//				logger.info("HttpClientUtils statusCode:"+response.getStatusLine().getStatusCode()+" url :"+url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            get.abort();//如果不想获取HTTPClient返回的信息  断开连接
        } finally {
            get.releaseConnection();
        }
        return null;
    }

    public static String doGet(String url, String params, String encode) throws Exception {
        byte[] is = doGet(url, params);
        if (is != null) {
            String body = IOUtils.toString(is, encode);
            return body;
        }
        return null;
    }

    /**
     * List <NameValuePair> nvps = new ArrayList <NameValuePair>();
     * nvps.add(new BasicNameValuePair("name", "1"));//名值对
     * nvps.add(new BasicNameValuePair("account", "xxxx"));
     *
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public static byte[] doPost(String url, List<NameValuePair> params) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setHeader("Connection", "close");
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        try {
            HttpResponse response = httpClient.execute(post);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                if (response.getEntity() != null) {
                    return EntityUtils.toByteArray(response.getEntity());
                }
            } else {
//				logger.info("HttpClientUtils statusCode:"+response.getStatusLine().getStatusCode()+" url :"+url);
                post.abort();//如果不想获取HTTPClient返回的信息  断开连接
            }
        } catch (Exception e) {
            e.printStackTrace();
            post.abort();//如果不想获取HTTPClient返回的信息  断开连接
        } finally {
            post.releaseConnection();//释放连接
        }
        return null;
    }

    public static String doPost(String url, List<NameValuePair> params, String encode) throws Exception {
        byte[] is = doPost(url, params);
        if (is != null) {
            String body = IOUtils.toString(is, encode);
            return body;
        }
        return null;
    }

    /**
     * 发送文件，map封装需要传入的参数和文件
     *
     * @param url
     * @param map
     * @throws IOException
     * @usage <pre>Map<String, Object> map = new HashMap<String, Object>(){
     * {
     * put("image", file);
     * put("filename", "iamaimage.jpg");
     * }
     * };
     * HttpClientUtils.doPost(url, map);</pre>
     */
    public static String doPost(String url, Map<String, Object> map) throws IOException {
        try {
            MultipartEntity entity = new MultipartEntity();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    FileBody bin = new FileBody(file);
                    entity.addPart(entry.getKey(), bin);
                } else {
                    entity.addPart(entry.getKey(), new StringBody(entry.getValue().toString(), Charset.forName("UTF-8")));
                }
            }
            byte[] is = doPost(url, entity);
            if (is != null) {
                String body = IOUtils.toString(is, CHARSET);
                return body;
            }
        } catch (UnsupportedEncodingException e) {
//			logger.debug("filename parse error, " + e.getMessage());
        }
        return "";
    }

    /**
     * 发送文件
     *
     * @param url
     * @param entity
     */
    public static byte[] doMultipartPost(String url, Map<String, Object> map) throws IOException {
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    FileBody bin = new FileBody(file);
                    entity.addPart(entry.getKey(), bin);
                } else {
                    entity.addPart(entry.getKey(), new StringBody(entry.getValue().toString(), Charset.forName("UTF-8")));
                }
            }
            byte[] is = doPost(url, entity);
            return is;
        } catch (UnsupportedEncodingException e) {
//			logger.debug("filename parse error, " + e.getMessage());
        }
        return "".getBytes();
    }

    /**
     * 编辑器office文件转换成HTML
     *
     * @param @param  url
     * @param @param  map
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @author: shun.yang
     * @Date :  2017年4月12日 下午4:05:21
     */
    public static String doOfficePost(String url, Map<String, Object> map) throws IOException {
        try {
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    FileBody bin = new FileBody(file);
                    entity.addPart(entry.getKey(), bin);
                } else {
                    entity.addPart(entry.getKey(), new StringBody(entry.getValue().toString(), Charset.forName("UTF-8")));
                }
            }
            byte[] is = doPost(url, entity);
            if (is != null) {
                String body = IOUtils.toString(is, "GB2312");
                return body;
            }
        } catch (UnsupportedEncodingException e) {
//    		logger.debug("filename parse error, " + e.getMessage());
        }
        return "";
    }

    /**
     * 发送文件，不推荐使用
     *
     * @param url
     * @param entity
     * @see doPost(String url, Map<String, Object> map)
     */
    private static byte[] doPost(String url, MultipartEntity entity) {
        HttpPost post = new HttpPost(url);
        try {
//			post.setHeader("Connection", "close"); 
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                EntityUtils.consume(entity);
//				logger.debug("MultipartEntity info send ok. ");
                if (response.getEntity() != null) {
                    return EntityUtils.toByteArray(response.getEntity());
                }
            } else {
                post.abort();//如果不想获取HTTPClient返回的信息  断开连接
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            post.abort();//如果不想获取HTTPClient返回的信息  断开连接
        } catch (IOException e) {
            e.printStackTrace();
            post.abort();//如果不想获取HTTPClient返回的信息  断开连接
        } finally {
            post.releaseConnection();//释放连接
        }
        return null;
    }

    public static byte[] doPost(String url, String json) throws Exception{
        HttpPost post = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(json, "utf-8");
        stringEntity.setContentEncoding("utf-8");
        post.setHeader("Content-type", "application/json");

        post.setEntity(stringEntity);

        return baseHttpRequest(url, post);
    }

    public static String doPost(String url, String json, String encode) throws Exception {
        byte[] is = doPost(url, json);
        if (is == null) {
            return null;
        }

        return IOUtils.toString(is, encode);
    }

    public static byte[] doDelete(String url, String params) {
        HttpDelete delete = new HttpDelete(url + (params == null ? "" : params));
        return baseHttpRequest(url, delete);
    }

    public static String doDelete(String url, String params, String encode) throws Exception {
        byte[] is = doDelete(url, params);
        if (is == null) {
            return null;
        }

        return IOUtils.toString(is, encode);
    }

    private static byte[] baseHttpRequest(String url, HttpRequestBase httpRequestBase) {
        try {
            HttpResponse response = httpClient.execute(httpRequestBase);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                if (response.getEntity() != null) {
                    return EntityUtils.toByteArray(response.getEntity());
                }
            } else {
                httpRequestBase.abort();//如果不想获取HTTPClient返回的信息  断开连接
//				logger.info("HttpClientUtils statusCode:" + response.getStatusLine().getStatusCode() + " url :" + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            httpRequestBase.abort();//如果不想获取HTTPClient返回的信息  断开连接
        } finally {
            httpRequestBase.releaseConnection();
        }
        return null;
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }
}
