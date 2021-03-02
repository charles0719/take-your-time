package com.charles;

import com.charles.util.HttpClientUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author charles
 * @date 2021/2/23 17:23
 */
public class App {
    private static final int total = 2000;
    private static final int currNum = 20;

    public static void main(String[] args) throws Exception {

//        System.out.println(testUrl());
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(total);
        Semaphore semaphore = new Semaphore(currNum);
        for (int i = 0; i < total; i++) {
            service.execute(()->{
                try {
                    semaphore.acquire();
//                    System.out.println(Thread.currentThread().getName());
                    try {
                        String result = testUrl();
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
            countDownLatch.countDown();
        }
        countDownLatch.await();
        service.shutdown();
    }

    private static String testUrl() throws Exception {
        //recordId=&type=0&categoryId=2c9082e473d6c56d0173dc9a163b007f&catetegoryBh=1001.1000&valueId=001614049946344000040050569b4a5d&objectId=8a209d1f7627b7d701764a7d048e0582
//        String url = "http://opposjzsk3.0.demo.xiaoi.com/kbaseui-std/article/markread.do";
        String url = "http://localhost:8080/kbaseui-std/article/markread.do";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("recordId", ""));
        params.add(new BasicNameValuePair("type", "0"));
        params.add(new BasicNameValuePair("jobnumber", "admin"));
        params.add(new BasicNameValuePair("categoryId", "2c9082e473d6c56d0173dc9a163b007f"));
        params.add(new BasicNameValuePair("catetegoryBh", "1001.1000"));
        params.add(new BasicNameValuePair("valueId", "001614049946344000040050569b4a5d"));
        params.add(new BasicNameValuePair("objectId", "8a209d1f7627b7d701764a7d048e0582"));
        String res = HttpClientUtils.doPost(url, params, "utf-8");
        return res;
    }
}
