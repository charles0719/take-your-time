package com.charles;

import com.charles.domain.Account;
import com.charles.domain.HistoryModel;
import com.charles.util.HttpClientUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import org.junit.Test;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author charles
 * @date 2021/3/21 14:57
 */
public class StockTest {

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

    @Test
    public void t20() throws Exception {
        double initial = 1.20;
        double count = 1000;
        System.out.println(initial * (1 + 5 / 100d));
        double value = 1.26d;
        if ((initial - value) / initial * 100 > 10) {
            count = count + 1000;
        } else if ((value - initial) / initial * 100 > 10) {
            count = count / 2;
        }
    }

    @Test
    public void t21() {
        //简单的马丁格尔策略1： 升值的趋势一直保持，就加仓，一旦回调，立马减去一半
        double[] data = {1.28, 1.26, 1.24, 1.22, 1.20, 1.22, 1.24};
        double start = data[0];
        int count = 1000;
        double bValue = start * count;//买入金钱
        double tValue = 100000;//剩余金钱
        for (int i = 1; i < data.length; i++) {
            //如果买入持仓不动，收益变化
            bValue = bValue + (data[i] - start) * count;
            start = data[i];
        }
        System.out.println(bValue);
    }

    @Test
    public void t22() {
        //简单的马丁格尔策略1： 升值的趋势一直保持，就加仓，一旦回调，立马减去一半
        double[] data = {1.20, 1.22, 1.24, 1.26, 1.28, 1.26, 1.24};
        double start = data[0];
        int count = 1000;
        double bValue = start * count;//买入金钱
        double tValue = 100000 - bValue;//剩余金钱
        double profit = 0d;
        Account account1 = Account.builder().flowAccount(bValue).profit(0d).count(count).build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        for (int i = 1; i < data.length; i++) {
            bValue = bValue + (data[i] - start) * count;
            profit += (data[i] - start) * count;
            if (data[i] > start) {
                bValue += 1000 * data[i];
                tValue -= 1000 * data[i];
                count += 1000;
            } else {
                count /= 2;
                bValue -= data[i] * count;
                tValue += data[i] * count;
            }
            Account account = Account.builder().flowAccount(bValue).profit(profit).count(count).build();
            accounts.add(account);
            start = data[i];
        }
        System.out.println(JSONArray.fromObject(accounts));
    }

    @Test
    public void t23() {
        //简单的马丁格尔策略2： 波动下行的趋势保持，就加仓，一旦回调，等收益持平，保证仓位不动，只需要1.24的时候就可以把收益稳住。
        double[] data = {1.28, 1.26, 1.24, 1.22, 1.20, 1.22, 1.24};
        double start = data[0];
        int count = 1000;
        double bValue = start * count;//买入金钱
        double profit = 0;
        Account account1 = Account.builder().flowAccount(bValue).profit(0d).count(count).build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        for (int i = 1; i < data.length; i++) {
            //如果买入持仓不动，收益变化
            bValue = bValue + (data[i] - start) * count;
            profit += (data[i] - start) * count;
            if (data[i] < start) {
                bValue += 1000 * data[i];
                count += 1000;
            } else {
//                count /= 2;
//                bValue -= data[i] * count;
            }
            Account account = Account.builder().flowAccount(bValue).profit(profit).count(count).build();
            accounts.add(account);
            start = data[i];
        }
        System.out.println(JSONArray.fromObject(accounts));
    }
}
