package com.charles.leeksmock.handler;

import com.charles.leeksmock.bean.CoinBean;
import com.charles.leeksmock.utils.HttpClientPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinaCoinHandler extends CoinRefreshHandler {
    public static final String URL = "http://hq.sinajs.cn/list=";
    private static final Pattern DEFAULT_STOCK_PATTERN = Pattern.compile("var hq_str_(\\w+?)=\"(.*?)\";");

    private static ScheduledExecutorService mSchedulerExecutor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void handle(List<String> code) {
        if (CollectionUtils.isEmpty(code)) {
            return;
        }
        useScheduleThreadExecutor(code);
    }

    public void useScheduleThreadExecutor(List<String> code) {
        if (mSchedulerExecutor.isShutdown()){
            mSchedulerExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        mSchedulerExecutor.scheduleAtFixedRate(getWork(code), 0, threadSleepTime, TimeUnit.SECONDS);
    }

    private Runnable getWork(List<String> code) {
        return () -> {
            pollStock(code);
        };
    }

    private void pollStock(List<String> code) {
        String params = StringUtils.join(code,',');
        System.out.println(1);
        try {
            String res = HttpClientPool.getHttpClient().get(URL + params);
//            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
//            System.out.printf("%s,%s%n", time, res);
            handleResponse(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleResponse(String response) {
        List<String> refreshTimeList = new ArrayList<>();
        for (String line : response.split("\n")) {
            Matcher matcher = DEFAULT_STOCK_PATTERN.matcher(line);
            if (!matcher.matches()) {
                continue;
            }
            String code = matcher.group(1);
            String[] split = matcher.group(2).split(",");
            if (split.length < 2) {//空数据跳过
                continue;
            }
            CoinBean bean = new CoinBean(code);
//            bean.setName(split[9]);
//            bean.setPrice(split[8]);
            bean.setTimeStamp(split[0]);
//            updateData(bean);
            refreshTimeList.add(split[0]);
        }

        System.out.println(refreshTimeList);
    }

}
