package com.charles.leeksmock.handler;

import com.charles.leeksmock.bean.StockBean;
import com.charles.leeksmock.utils.HttpClientPool;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class TencentStockHandler extends StockRefreshHandler {
    private String urlPara;

    private Thread worker;


    public TencentStockHandler() {
    }

    @Override
    public void handle(List<String> code) {

        if (worker!=null){
            worker.interrupt();
        }
        if (code.isEmpty()){
            return;
        }
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (worker!=null && worker.hashCode() == Thread.currentThread().hashCode() && !worker.isInterrupted()){
                    stepAction();
                    try {
                        Thread.sleep(threadSleepTime * 1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
        urlPara = String.join(",", code);
        worker.start();

    }

    public void stopHandle() {
        if (worker != null) {
            worker.interrupt();
        }
    }

    private void stepAction() {
//        Date now = new Date();
//        if ( now.getHours() < 9 || now.getHours() > 16){//九点到下午4点才更新数据
//            try {
//                Thread.sleep(60 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
        if (StringUtils.isEmpty(urlPara)){
            return;
        }
        try {
            String result = HttpClientPool.getHttpClient().get("http://qt.gtimg.cn/q="+urlPara);
            parse(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parse(String result) {
        String[] lines = result.split("\n");
        for (String line : lines) {
            String code = line.substring(line.indexOf("_")+1,line.indexOf("="));
            String dataStr = line.substring(line.indexOf("=")+2,line.length()-2);
            String[] values = dataStr.split("~");
            StockBean bean = new StockBean(code);
            bean.setName(values[1]);
            bean.setNow(values[3]);
            bean.setChange(values[31]);
            bean.setChangePercent(values[32]);
            bean.setTime(values[30]);
            bean.setMax(values[33]);//33
            bean.setMin(values[34]);//34
            System.out.println(bean);
        }
    }
}
