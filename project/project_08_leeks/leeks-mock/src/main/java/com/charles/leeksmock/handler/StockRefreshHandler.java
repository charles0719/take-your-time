package com.charles.leeksmock.handler;

import com.charles.leeksmock.utils.WindowUtils;
import java.util.List;
import java.util.Objects;

public abstract class StockRefreshHandler  {
    private static String[] columnNames;

    static {
        String[] configStr = Objects.requireNonNull(WindowUtils.STOCK_TABLE_HEADER_KEY).split(",");
        columnNames = new String[configStr.length];
        for (int i = 0; i < configStr.length; i++) {
            columnNames[i] = configStr[i];
        }
    }

    /**
     * 更新数据的间隔时间（秒）
     */
    protected volatile int threadSleepTime = 10;

    /**
     * 从网络更新数据
     *
     * @param code
     */
    public abstract void handle(List<String> code);

}
