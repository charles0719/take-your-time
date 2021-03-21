package com.charles;

import com.charles.domain.HistoryModel;
import com.charles.util.HttpClientUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author charles
 * @date 2021/3/21 14:57
 */
public class StockTest {

    @Test
    public void t20() throws Exception {
        double initial = 1.20;
        System.out.println(initial * (1 + 5 / 100d));
    }

    @Test
    public void t6() throws Exception {
        String url = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=sz000001&scale=5&ma=5&datalen=1023";
        String result = HttpClientUtils.doGet(url, "", "utf-8");

        Type type = new TypeToken<List<HistoryModel>>() {
        }.getType();
        //获取的数据是类似下面的json数组：日期、开盘价、最高价、最低价、收盘价、成交量，成交额，换手。
        List<HistoryModel> historyList = new Gson().fromJson(result, type);
        for (int j = 0; j < historyList.size(); j++) {
            if (historyList.get(j).getDay().split(" ")[1].equals("15:00:00")) {
                System.out.println(historyList.get(j));
            }
        }
//        System.out.println(historyList);
    }

    @Test
    public void t7() throws Exception {
        //code：股票代码，以cn_开头，start:起始时间，end：截止时间，stat:统计信息，为0的时候就不返回stat对应的值了，order：排序方法（D表示降序排，A表示升序排），period：数据周期（d表示日线，m表示月线，w表示周线）。
        String url = "http://q.stock.sohu.com/hisHq?code=cn_600009&start=20180716&end=20210319&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp";
        String result = HttpClientUtils.doGet(url, "", "utf-8");
        //返回的数据以这条为例"2018-07-20","61.22","61.83","0.61","1.00%","61.22","62.69","57637","35856.55","0.53%"分别表示日期，开盘，收盘，涨跌，涨幅，最低，最高，成交量，成交额，换手。
        System.out.println(result);
    }


}
