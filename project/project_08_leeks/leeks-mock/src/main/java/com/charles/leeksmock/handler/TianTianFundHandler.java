package com.charles.leeksmock.handler;
import com.charles.leeksmock.bean.FundBean;
import com.charles.leeksmock.utils.HttpClientPool;
import com.google.gson.Gson;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TianTianFundHandler extends FundRefreshHandler {
    public static final String URL = "http://fundgz.1234567.com.cn/js/";
    public final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final List<String> codes = new ArrayList<>();
    private static Gson gson = new Gson();
    private Thread worker;
    /**
     * 更新数据的间隔时间（秒）
     */
    private volatile int threadSleepTime = 60;

    public TianTianFundHandler() {

    }

    @Override
    public void handle(List<String> code) {
        if (code.isEmpty()) {
            return;
        }

        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (worker != null && worker.hashCode() == Thread.currentThread().hashCode() && !worker.isInterrupted()) {
                    synchronized (codes) {
                        stepAction();
                    }
                    try {
                        Thread.sleep(threadSleepTime * 1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
        synchronized (codes) {
            codes.clear();
            codes.addAll(code);
        }
        worker.start();
    }


    private void stepAction() {
        for (String s : codes) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HttpClientPool.getHttpClient().get(URL + s + ".js?rt=" + System.currentTimeMillis());
                        String json = result.substring(8, result.length() - 2);
                        if (!json.isEmpty()) {
                            FundBean bean = gson.fromJson(json, FundBean.class);
                            System.out.println(bean);
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public int getThreadSleepTime() {
        return threadSleepTime;
    }

    public void setThreadSleepTime(int threadSleepTime) {
        this.threadSleepTime = threadSleepTime;
    }
}
