package com.charles;

import com.alibaba.excel.EasyExcel;
import com.charles.domain.AvgFundDto;
import com.charles.domain.HistoryDto;
import com.charles.domain.HistoryModel;
import com.charles.util.HttpClientUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * @author charles
 * @date 2021/3/21 23:47
 */
public class DanJuanTest {

    @Test
    public void t1() throws Exception {
        String code = "161725";
        String size = "365";
        String url = "https://danjuanapp.com/djapi/fund/nav/history/" + code + "?size=" + size + "&page=1";
        String res = HttpClientUtils.doGet(url, "", "utf-8");
        System.out.println(res);
        new Gson().toJson(res);

//        s = s['data']['items']
        //1.月初和月末对比
        //2.当月最高涨和最低跌
        //3.当月波动值（最高涨和最低跌之差）
        //4.月差值(月末减月初，该月是否盈亏）
        // 5. 熟悉一下echarts
    }

    @Test
    public void t2() throws Exception {
//         {"股票型":1,"混合型":3,"债券型":2,"指数型":5,"QDII型":11}
        //dict_time={'近一周':'1w','近一月':'1m','近三月':'3m','近六月':'6m','近1年':'1y','近2年':'2y','近3年':'3y','近5年':'5y'}
        String url1 = "https://danjuanapp.com/djapi/v3/filter/fund?type=" + 1 + "&order_by=1m&size=20&page=1";
        String res = HttpClientUtils.doGet(url1, "", "utf-8");
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("items");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
//            System.out.println(obj.getString("fd_code") + "," + obj.getString("fd_name"));
            map.put(obj.getString("fd_code"), obj.getString("fd_name"));
        }

    }

    @Test
    public void t3() throws Exception {
        String url2 = "https://danjuanapp.com/djapi/fund/nav/history/%s?page=1&size=180";
        String[] keys = "110011,005445,008099,005937,570001,166005,166019,163406,005267,260108".split(",");
        for (String key : keys) {
            String url = String.format(url2, key);
            String historyData = HttpClientUtils.doGet(url, "", "utf-8");
            JSONArray historyArray = JSONObject.fromObject(historyData).getJSONObject("data").getJSONArray("items");
            Map<String, Double> dataMap = new LinkedHashMap<>();
            double data = 0d;
            List<HistoryDto> historys = new ArrayList<>();
            for (int i = historyArray.size() - 1; i >= 0; i--) {
                JSONObject item = historyArray.getJSONObject(i);
                data += item.optDouble("percentage");
                dataMap.put(item.getString("date"), data);
                HistoryDto model = HistoryDto.builder().day(item.getString("date")).code1(String.format("%.2f", data)).build();
                historys.add(model);
            }
            EasyExcel.write("D://history_" + key + ".xlsx", HistoryModel.class).needHead(true).sheet("收益").doWrite(historys);

//            System.out.println(dataMap);
        }


    }


}
